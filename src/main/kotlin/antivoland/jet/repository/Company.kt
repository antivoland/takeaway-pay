package antivoland.jet.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Company(@Id val id: String, @OneToOne val account: Account, val dailyAllowanceLimit: Double)