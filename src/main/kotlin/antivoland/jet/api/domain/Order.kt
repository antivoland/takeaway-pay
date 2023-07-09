package antivoland.jet.api.domain

import com.fasterxml.jackson.annotation.JsonProperty

class Order(
    @get:JsonProperty("restaurant_id") @param:JsonProperty("restaurant_id") val restaurantId: String,
    @get:JsonProperty("amount") @param:JsonProperty("amount") val amount: Double
)