package antivoland.jet.service

import antivoland.jet.api.domain.Order
import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.repository.AccountRepository
import antivoland.jet.repository.CustomerRepository
import antivoland.jet.repository.RestaurantRepository
import org.hibernate.StaleObjectStateException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val restaurantRepository: RestaurantRepository,
    val accountRepository: AccountRepository
) {
    fun balance(id: String): BigDecimal = customerRepository
        .findById(id)
        .orElseThrow { CustomerNotFoundException(id) }
        .account
        .balance

    @Transactional
    @Retryable(value = [StaleObjectStateException::class], maxAttempts = 10, backoff = Backoff(delay = 100))
    fun pay(id: String, order: Order) {
        val customer = customerRepository
            .findById(id)
            .orElseThrow { CustomerNotFoundException(id) }
        val restaurant = restaurantRepository
            .findById(order.restaurantId)
            .orElseThrow { RestaurantNotFoundException(order.restaurantId) }
        if (customer.account.balance < order.amount) throw CustomerHasInsufficientFundsException(id)
        accountRepository.save(customer.account.add(-order.amount))
        accountRepository.save(restaurant.account.add(order.amount))
    }
}