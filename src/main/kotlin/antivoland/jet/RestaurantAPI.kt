package antivoland.jet

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("restaurants")
class RestaurantAPI {
    @GetMapping("{id}/balance")
    fun balance(@PathVariable id: String) = "Not implemented yet"
}