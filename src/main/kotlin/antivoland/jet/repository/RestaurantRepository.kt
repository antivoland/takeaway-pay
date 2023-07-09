package antivoland.jet.repository

import antivoland.jet.repository.entity.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, String>