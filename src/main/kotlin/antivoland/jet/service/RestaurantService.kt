package antivoland.jet.service

import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.repository.RestaurantRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class RestaurantService(val restaurantRepository: RestaurantRepository) {
    fun balance(id: String): BigDecimal = restaurantRepository
        .findById(id)
        .orElseThrow { RestaurantNotFoundException(id) }
        .account
        .balance
}