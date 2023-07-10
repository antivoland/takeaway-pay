package antivoland.jet.api

import antivoland.jet.api.domain.Order
import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.CustomerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("customers")
class CustomerAPI(val customerService: CustomerService) {
    @Operation(summary = "Get customer's balance")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successful",
                content = [Content(schema = Schema(implementation = BigDecimal::class), mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(implementation = String::class), mediaType = "application/json")]
            )
        ]
    )
    @ResponseStatus(OK)
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = customerService.balance(id)

    @Operation(summary = "Make customer's payment")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Successful",
                content = [Content(schema = Schema(implementation = Unit::class), mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer or restaurant not found",
                content = [Content(schema = Schema(implementation = String::class), mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "418",
                description = "Customer has insufficient funds",
                content = [Content(schema = Schema(implementation = String::class), mediaType = "application/json")]
            )
        ]
    )
    @ResponseStatus(CREATED)
    @PostMapping("{id}/pay")
    fun pay(@PathVariable id: String, @RequestBody order: Order) = customerService.pay(id, order)

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CustomerNotFoundException::class)
    fun customerNotFound(e: CustomerNotFoundException): String = e.message!!

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException::class)
    fun restaurantNotFound(e: RestaurantNotFoundException): String = e.message!!

    @ResponseStatus(I_AM_A_TEAPOT)
    @ExceptionHandler(CustomerHasInsufficientFundsException::class)
    fun customerHasInsufficientFunds(e: CustomerHasInsufficientFundsException): String = e.message!!
}