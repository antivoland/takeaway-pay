package antivoland.jet.repository

import antivoland.jet.repository.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, String>