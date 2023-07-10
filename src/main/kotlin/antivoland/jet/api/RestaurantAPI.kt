package antivoland.jet.api

import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantAPI(val restaurantService: RestaurantService) {
    @ResponseStatus(OK)
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = restaurantService.balance(id)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException::class)
    fun restaurantNotFound(e: RestaurantNotFoundException): String = e.message!!
}