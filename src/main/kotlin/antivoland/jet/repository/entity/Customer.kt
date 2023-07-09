package antivoland.jet.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Customer(
    @Id val id: String,
    @OneToOne val account: Account,
    val allowanceLimit: Double,
    @OneToMany(mappedBy = "customer") val payments: List<Payment>
) {
    fun balance() = allowanceLimit - payments
        .stream()
        .mapToDouble { payment -> payment.amount }
        .sum()
}