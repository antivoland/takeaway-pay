package antivoland.jet.repository

import antivoland.jet.domain.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, String>