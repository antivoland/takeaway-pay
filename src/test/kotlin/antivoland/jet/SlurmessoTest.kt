package antivoland.jet

import antivoland.jet.api.domain.Order
import antivoland.jet.api.domain.Receipt
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

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SlurmessoTest(@Autowired val api: TestRestTemplate) {
    companion object {
        val CREW: List<String> = listOf(
            "fry",
            "leela",
            "bender",
            "amy",
            "hermes",
            "professor",
            "zoidberg",
            "scruffy"
        )
        val SLURM_PRICE: Double = .25
        val ESPRESSO_PRICE: Double = .75
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

        assertThat(restaurantBalance("slurm-dispensing-unit"))
            .isCloseTo(20.0, offset(0.01))
        assertThat(restaurantBalance("coffee-maker-3000"))
            .isCloseTo(60.0, offset(0.01))
    }

    private fun drink(start: CountDownLatch, finish: CountDownLatch, id: String): Runnable {
        return Runnable {
            try {
                start.await()
                var orderNo = 0;
                var response: ResponseEntity<Receipt>
                do {
                    ++orderNo;
                    response =
                        if (orderNo % 2 == 0) customerPay(id, Order("slurm-dispensing-unit", SLURM_PRICE))
                        else customerPay(id, Order("coffee-maker-3000", ESPRESSO_PRICE))
                } while (response.statusCode == OK)
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

    fun customerPay(id: String, order: Order): ResponseEntity<Receipt> =
        api.postForEntity("/customers/$id/pay", order, Receipt::class.java)
}