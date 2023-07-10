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
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.math.BigDecimal

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
        val customerAccount = Account("customer-account", BigDecimal("1.2"), 3)
        entityManager.persist(customerAccount)
        entityManager.persist(Customer("customer", customerAccount))
        entityManager.flush()

        assertThat(service.balance("customer")).isEqualByComparingTo(BigDecimal("1.2"))
    }

    @Test
    fun testBalanceWhenCustomerNotFound() {
        assertThatThrownBy { service.balance("customer") }
            .isInstanceOf(CustomerNotFoundException::class.java)
            .hasMessage("Customer 'customer' not found")
    }

    @Test
    fun testPay() {
        val customerAccount = Account("customer-account", BigDecimal("1.2"), 3)
        val restaurantAccount = Account("restaurant-account", BigDecimal("4.5"), 6)
        entityManager.persist(customerAccount)
        entityManager.persist(restaurantAccount)
        entityManager.persist(Restaurant("restaurant", restaurantAccount))
        entityManager.persist(Customer("customer", customerAccount))
        entityManager.flush()

        service.pay("customer", Order("restaurant", BigDecimal("1.1")))
        entityManager.flush()

        val updatedCustomerAccount = accountRepository
            .findById("customer-account")
            .orElseThrow()
        assertThat(updatedCustomerAccount.id).isEqualTo("customer-account")
        assertThat(updatedCustomerAccount.balance).isEqualByComparingTo(BigDecimal("0.1"))
        assertThat(updatedCustomerAccount.version).isEqualTo(4)

        val updatedRestaurantAccount = accountRepository
            .findById("restaurant-account")
            .orElseThrow()
        assertThat(updatedRestaurantAccount.id).isEqualTo("restaurant-account")
        assertThat(updatedRestaurantAccount.balance).isEqualByComparingTo(BigDecimal("5.6"))
        assertThat(updatedRestaurantAccount.version).isEqualTo(7)
    }

    @Test
    fun testPayWhenCustomerNotFound() {
        val restaurantAccount = Account("restaurant-account", BigDecimal("4.5"), 6)
        entityManager.persist(restaurantAccount)
        entityManager.persist(Restaurant("restaurant", restaurantAccount))
        entityManager.flush()

        assertThatThrownBy { service.pay("customer", Order("restaurant", BigDecimal("1.1"))) }
            .isInstanceOf(CustomerNotFoundException::class.java)
            .hasMessage("Customer 'customer' not found")
    }

    @Test
    fun testPayWhenRestaurantNotFound() {
        val customerAccount = Account("customer-account", BigDecimal("1.2"), 3)
        entityManager.persist(customerAccount)
        entityManager.persist(Customer("customer", customerAccount))
        entityManager.flush()

        assertThatThrownBy { service.pay("customer", Order("restaurant", BigDecimal("1.1"))) }
            .isInstanceOf(RestaurantNotFoundException::class.java)
            .hasMessage("Restaurant 'restaurant' not found")
    }

    @Test
    fun testPayWhenCustomerHasInsufficientFunds() {
        val customerAccount = Account("customer-account", BigDecimal("1.2"), 3)
        val restaurantAccount = Account("restaurant-account", BigDecimal("4.5"), 6)
        entityManager.persist(customerAccount)
        entityManager.persist(restaurantAccount)
        entityManager.persist(Restaurant("restaurant", restaurantAccount))
        entityManager.persist(Customer("customer", customerAccount))
        entityManager.flush()

        assertThatThrownBy { service.pay("customer", Order("restaurant", BigDecimal("1.21"))) }
            .isInstanceOf(CustomerHasInsufficientFundsException::class.java)
            .hasMessage("Customer 'customer' has insufficient funds")
    }
}