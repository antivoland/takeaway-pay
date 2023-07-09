package antivoland.jet.service

import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.repository.AccountRepository
import antivoland.jet.repository.CustomerRepository
import antivoland.jet.repository.PaymentRepository
import antivoland.jet.repository.RestaurantRepository
import antivoland.jet.repository.entity.Payment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CustomerService(
    val customerRepository: CustomerRepository,
    val restaurantRepository: RestaurantRepository,
    val accountRepository: AccountRepository,
    val paymentRepository: PaymentRepository
) {
    fun balance(id: String): Double = customerRepository
        .findById(id)
        .orElseThrow { CustomerNotFoundException(id) }
        .balance()

    fun pay(id: String, restaurantId: String, amount: Double): Payment {
        val customer = customerRepository
            .findById(id)
            .orElseThrow { CustomerNotFoundException(id) }
        val restaurant = restaurantRepository
            .findById(restaurantId)
            .orElseThrow { RestaurantNotFoundException(restaurantId) }
        if (customer.balance().compareTo(amount) < 0)
            throw CustomerHasInsufficientFundsException(id)
        accountRepository.save(customer.account.add(-amount))
        accountRepository.save(restaurant.account.add(amount))
        return paymentRepository.save(Payment(customer, amount))
    }
}