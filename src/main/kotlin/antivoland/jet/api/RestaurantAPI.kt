package antivoland.jet.api

import antivoland.jet.service.RestaurantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantAPI(val restaurantService: RestaurantService) {
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = restaurantService.balance(id)
}