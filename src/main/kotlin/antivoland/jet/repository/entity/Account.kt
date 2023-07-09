package antivoland.jet.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version

@Entity
class Account(@Id val id: String, val balance: Double, @Version val version: Int) {
    fun add(amount: Double): Account = Account(id, balance + amount, version)

    override fun toString() = "Account(id='$id', balance=$balance, version=$version)"
}