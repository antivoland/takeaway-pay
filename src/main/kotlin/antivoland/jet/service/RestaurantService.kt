package antivoland.jet.service

import antivoland.jet.repository.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class RestaurantService(val restaurantRepository: RestaurantRepository) {
    fun balance(id: String): Double = restaurantRepository.get(id).account.balance
}