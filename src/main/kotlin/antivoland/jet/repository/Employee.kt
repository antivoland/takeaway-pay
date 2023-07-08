package antivoland.jet.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
class Employee(@Id val id: String, @OneToOne val company: Company)