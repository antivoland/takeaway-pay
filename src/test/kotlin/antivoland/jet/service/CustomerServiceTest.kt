package antivoland.jet.service

import antivoland.jet.api.domain.Order
import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.repository.AccountRepository
import antivoland.jet.repository.CustomerRepository
import antivoland.jet.repository.RestaurantRepository
import antivoland.jet.repository.entity.Account
import antivoland.jet.repository.entity.Customer
import antivoland.jet.repository.entity.Restaurant
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.data.Offset.offset
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class CustomerServiceTest(
    @Autowired val customerRepository: CustomerRepository,
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val accountRepository: AccountRepository,
    @Autowired val entityManager: TestEntityManager
) {
    val service by lazy { CustomerService(customerRepository, restaurantRepository, accountRepository) }

    @Test
    fun testBalance() {
        val customerAccount = Account("customer-account", 1.2, 3)
        entityManager.persistAndFlush(customerAccount)
        entityManager.persistAndFlush(Customer("customer", customerAccount))

        assertThat(service.balance("customer")).isCloseTo(1.2, offset(0.01))
    }

    @Test
    fun testBalanceWhenCustomerNotFound() {
        assertThatThrownBy { service.balance("customer") }
            .isInstanceOf(CustomerNotFoundException::class.java)
            .hasMessage("Customer 'customer' not found")
    }

    @Test
    fun testPay() {
        val customerAccount = Account("customer-account", 1.2, 3)
        val restaurantAccount = Account("restaurant-account", 4.5, 6)
        entityManager.persistAndFlush(customerAccount)
        entityManager.persistAndFlush(restaurantAccount)
        entityManager.persistAndFlush(Restaurant("restaurant", restaurantAccount))
        entityManager.persistAndFlush(Customer("customer", customerAccount))

        service.pay("customer", Order("restaurant", 1.1))

        assertThat(accountRepository.findById("customer-account").orElseThrow())
            .isEqualTo(Account("customer-account", 0.1, 4))
        assertThat(accountRepository.findById("restaurant-account").orElseThrow())
            .isEqualTo(Account("restaurant-account", 5.6, 7))
    }

    @Test
    fun testPayWhenCustomerNotFound() {
        val restaurantAccount = Account("restaurant-account", 4.5, 6)
        entityManager.persistAndFlush(restaurantAccount)
        entityManager.persistAndFlush(Restaurant("restaurant", restaurantAccount))

        assertThatThrownBy { service.pay("customer", Order("restaurant", 1.1)) }
            .isInstanceOf(CustomerNotFoundException::class.java)
            .hasMessage("Customer 'customer' not found")
    }

    @Test
    fun testPayWhenRestaurantNotFound() {
        val customerAccount = Account("customer-account", 1.2, 3)
        entityManager.persistAndFlush(customerAccount)
        entityManager.persistAndFlush(Customer("customer", customerAccount))

        assertThatThrownBy { service.pay("customer", Order("restaurant", 1.1)) }
            .isInstanceOf(RestaurantNotFoundException::class.java)
            .hasMessage("Restaurant 'restaurant' not found")
    }

    @Test
    fun testPayWhenCustomerHasInsufficientFunds() {
        val customerAccount = Account("customer-account", 1.2, 3)
        val restaurantAccount = Account("restaurant-account", 4.5, 6)
        entityManager.persistAndFlush(customerAccount)
        entityManager.persistAndFlush(restaurantAccount)
        entityManager.persistAndFlush(Restaurant("restaurant", restaurantAccount))
        entityManager.persistAndFlush(Customer("customer", customerAccount))

        assertThatThrownBy { service.pay("customer", Order("restaurant", 1.21)) }
            .isInstanceOf(CustomerHasInsufficientFundsException::class.java)
            .hasMessage("Customer 'customer' has insufficient funds")
    }
}