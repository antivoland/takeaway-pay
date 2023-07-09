package antivoland.jet.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Payment(
    @ManyToOne val customer: Customer,
    val amount: Double,
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long = 0
)