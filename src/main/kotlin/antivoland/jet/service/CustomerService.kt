package antivoland.jet.service

import antivoland.jet.domain.Payment
import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.repository.AccountRepository
import antivoland.jet.repository.CustomerRepository
import antivoland.jet.repository.PaymentRepository
import antivoland.jet.repository.RestaurantRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class CustomerService(
    val customerRepository: CustomerRepository,
    val restaurantRepository: RestaurantRepository,
    val accountRepository: AccountRepository,
    val paymentRepository: PaymentRepository
) {
    fun balance(id: String): Double = customerRepository.get(id).balance()

    fun pay(id: String, restaurantId: String, amount: Double): Payment {
        val customer = customerRepository.get(id)
        val restaurant = restaurantRepository.get(restaurantId)
        if (customer.balance().compareTo(amount) < 0) throw CustomerHasInsufficientFundsException(id)
        accountRepository.save(customer.account.add(-amount))
        accountRepository.save(restaurant.account.add(amount))
        return paymentRepository.save(Payment(id, amount))
    }
}