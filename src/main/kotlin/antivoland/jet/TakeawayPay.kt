package antivoland.jet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TakeawayPay

fun main(vararg args: String) {
    runApplication<TakeawayPay>(*args)
}