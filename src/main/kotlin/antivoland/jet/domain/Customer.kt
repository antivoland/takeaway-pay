package antivoland.jet.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Customer(
    @Id val id: String,
    @OneToOne val account: Account,
    val allowanceLimit: Double,
    @OneToMany val payments: List<Payment>
) {
    fun balance() = allowanceLimit - payments
        .stream()
        .mapToDouble { payment -> payment.amount }
        .sum()
}