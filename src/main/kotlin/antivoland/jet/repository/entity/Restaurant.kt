package antivoland.jet.repository.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Restaurant(@Id val id: String, @OneToOne val account: Account) {
    override fun toString() = "Restaurant(id='$id', account=$account)"
}