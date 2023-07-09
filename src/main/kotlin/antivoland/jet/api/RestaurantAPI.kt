package antivoland.jet.api

import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.RestaurantService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantAPI(val restaurantService: RestaurantService) {
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = restaurantService.balance(id)

    @ExceptionHandler(RestaurantNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun restaurantNotFound(e: RestaurantNotFoundException): String = e.message!!
}