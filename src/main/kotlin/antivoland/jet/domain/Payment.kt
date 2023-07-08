package antivoland.jet.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class Payment(
    val customerId: String,
    val amount: Double,
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long? = null
)