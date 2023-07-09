package antivoland.jet.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Account(
    @Id val id: String,
    val balance: Double
) {
    fun add(amount: Double): Account = Account(id, balance + amount)
}