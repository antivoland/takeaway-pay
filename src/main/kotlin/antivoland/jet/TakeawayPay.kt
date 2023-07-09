package antivoland.jet

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class TakeawayPay

fun main(vararg args: String) {
    SpringApplication.run(TakeawayPay::class.java, *args)
}