package antivoland.jet.api.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

class Order(
    @get:JsonProperty("restaurant_id") @param:JsonProperty("restaurant_id") val restaurantId: String,
    @get:JsonProperty("amount") @param:JsonProperty("amount") val amount: BigDecimal
) {
    override fun toString() = "Order(restaurantId='$restaurantId', amount=$amount)"
}