package antivoland.jet.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Version
import java.math.BigDecimal

@Entity
class Account(@Id val id: String, val balance: BigDecimal, @Version val version: Int) {
    fun add(amount: BigDecimal): Account = Account(id, balance.add(amount), version)

    override fun toString() = "Account(id='$id', balance=$balance, version=$version)"
}