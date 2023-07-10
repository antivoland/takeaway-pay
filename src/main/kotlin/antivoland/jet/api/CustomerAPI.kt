package antivoland.jet.api

import antivoland.jet.api.domain.Order
import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerAPI(val customerService: CustomerService) {
    @ResponseStatus(OK)
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = customerService.balance(id)

    @ResponseStatus(CREATED)
    @PostMapping("{id}/pay")
    fun pay(@PathVariable id: String, @RequestBody order: Order) = customerService.pay(id, order)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException::class)
    fun customerNotFound(e: CustomerNotFoundException): String = e.message!!

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException::class)
    fun restaurantNotFound(e: RestaurantNotFoundException): String = e.message!!

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerHasInsufficientFundsException::class)
    fun customerHasInsufficientFunds(e: CustomerHasInsufficientFundsException): String = e.message!!
}