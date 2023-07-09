package antivoland.jet.api.domain

import com.fasterxml.jackson.annotation.JsonProperty

class Receipt(
    @get:JsonProperty("id") @param:JsonProperty("id") val id: Long,
    @get:JsonProperty("amount") @param:JsonProperty("amount") val amount: Double
)