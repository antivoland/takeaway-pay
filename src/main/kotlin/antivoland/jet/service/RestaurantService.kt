package antivoland.jet.service

import antivoland.jet.repository.RestaurantRepository
import org.springframework.stereotype.Service

@Service
class RestaurantService(val restaurantRepository: RestaurantRepository) {
    fun balance(id: String): Double? = restaurantRepository
        .findById(id)
        .map { restaurant -> restaurant.account.balance }
        .orElse(null)
}