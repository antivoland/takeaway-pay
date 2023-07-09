package antivoland.jet

import antivoland.jet.api.domain.Order
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset.offset
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/*
 Slurmesso is a delicious and highly addictive drink. You'll need to mix one
 portion of slurm and one portion of espresso. You won't be able to resist
 another slurmesso right after you've had your current one.

 Professor Farnsworth ordered slurm dispensing unit from MomCorp and rented
 a Coffee Maker 3000 from Family Bros. Pizza. Both devices are capable to serve
 all the crew members simultaneously.

 The cost of one portion of slurm is 0.25 cents, and one espresso will cost
 0.75 cents. Thus, every employee of Planet Express is now very busy cyclically
 collecting ingredients, mixing them and drinking the slurmesso.
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SlurmessoTest(@Autowired val api: TestRestTemplate) {
    companion object {
        val CREW = listOf(
            "fry",
            "leela",
            "bender",
            "amy",
            "hermes",
            "professor",
            "zoidberg",
            "scruffy"
        )
        val SLURM = Order("slurm-dispensing-unit", .25)
        val ESPRESSO = Order("coffee-maker-3000", .75)
    }

    @Test
    @Throws(Exception::class)
    fun test() {
        val threadPool = Executors.newFixedThreadPool(CREW.size);
        try {
            val start = CountDownLatch(1)
            val finish = CountDownLatch(CREW.size)
            CREW.stream().map { id -> drink(start, finish, id) }.forEach { task -> threadPool.submit(task) }
            start.countDown()
            finish.await()
        } finally {
            threadPool.shutdown()
        }
        for (id in CREW) assertThat(customerBalance(id)).isCloseTo(0.0, offset(0.01))
        assertThat(restaurantBalance("slurm-dispensing-unit")).isCloseTo(20.0, offset(0.01))
        assertThat(restaurantBalance("coffee-maker-3000")).isCloseTo(60.0, offset(0.01))
    }

    private fun drink(start: CountDownLatch, finish: CountDownLatch, id: String): Runnable {
        return Runnable {
            try {
                start.await()
                var orderNo = 0;
                var response: ResponseEntity<Unit>
                do response = customerPay(id, if (++orderNo % 2 == 0) SLURM else ESPRESSO)
                while (response.statusCode == OK)
            } finally {
                finish.countDown()
            }
        }
    }

    fun restaurantBalance(id: String): Double {
        val response = api.getForEntity("/restaurants/$id/balance", Double::class.java)
        assertThat(response.statusCode).isEqualTo(OK)
        return response.body!!
    }

    fun customerBalance(id: String): Double {
        val response = api.getForEntity("/customers/$id/balance", Double::class.java)
        assertThat(response.statusCode).isEqualTo(OK)
        return response.body!!
    }

    fun customerPay(id: String, order: Order): ResponseEntity<Unit> =
        api.postForEntity("/customers/$id/pay", order, Unit::class.java)
}