package antivoland.jet.api

import antivoland.jet.api.domain.Order
import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerAPI(val customerService: CustomerService) {
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = customerService.balance(id)

    @PostMapping("{id}/pay")
    fun pay(@PathVariable id: String, @RequestBody order: Order) = customerService.pay(id, order)

    @ExceptionHandler(CustomerNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun customerNotFound(e: CustomerNotFoundException): String = e.message!!

    @ExceptionHandler(RestaurantNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun restaurantNotFound(e: RestaurantNotFoundException): String = e.message!!

    @ExceptionHandler(CustomerHasInsufficientFundsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun customerHasInsufficientFunds(e: CustomerHasInsufficientFundsException): String = e.message!!
}