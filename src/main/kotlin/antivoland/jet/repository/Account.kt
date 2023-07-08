package antivoland.jet.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Account(@Id val id: String, val balance: Double)