package antivoland.jet.api

import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.RestaurantService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("restaurants")
class RestaurantAPI(val restaurantService: RestaurantService) {
    @Operation(summary = "Get restaurant's balance")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successful",
                content = [Content(schema = Schema(implementation = BigDecimal::class), mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Restaurant not found",
                content = [Content(schema = Schema(implementation = String::class), mediaType = "application/json")]
            )]
    )
    @ResponseStatus(OK)
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = restaurantService.balance(id)

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException::class)
    fun restaurantNotFound(e: RestaurantNotFoundException): String = e.message!!
}