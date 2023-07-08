package antivoland.jet.repository

import antivoland.jet.domain.Restaurant
import antivoland.jet.exception.RestaurantNotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, String> {
    fun get(id: String): Restaurant = findById(id).orElseThrow { RestaurantNotFoundException(id) }
}