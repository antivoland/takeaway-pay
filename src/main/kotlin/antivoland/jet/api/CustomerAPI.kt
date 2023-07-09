package antivoland.jet.api

import antivoland.jet.api.domain.Order
import antivoland.jet.api.domain.Receipt
import antivoland.jet.service.CustomerService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerAPI(val customerService: CustomerService) {
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String): Double = customerService.balance(id)

    @PostMapping("{id}/pay")
    fun pay(@PathVariable id: String, @RequestBody order: Order): Receipt {
        val payment = customerService.pay(id, order.restaurantId, order.amount)
        return Receipt(payment.id, payment.amount)
    }
}