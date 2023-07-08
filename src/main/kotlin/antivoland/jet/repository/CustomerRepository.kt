package antivoland.jet.repository

import antivoland.jet.domain.Customer
import antivoland.jet.exception.CustomerNotFoundException
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, String> {
    fun get(id: String): Customer = findById(id).orElseThrow { CustomerNotFoundException(id) }
}