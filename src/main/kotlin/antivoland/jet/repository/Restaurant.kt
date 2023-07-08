package antivoland.jet.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Restaurant(@Id val id: String, @OneToOne val account: Account)