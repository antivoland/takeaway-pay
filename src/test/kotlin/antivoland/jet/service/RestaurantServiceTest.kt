package antivoland.jet.service

import antivoland.jet.repository.RestaurantRepository
import antivoland.jet.repository.entity.Account
import antivoland.jet.repository.entity.Restaurant
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.math.BigDecimal

@DataJpaTest
class RestaurantServiceTest(
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val entityManager: TestEntityManager
) {
    val service by lazy { RestaurantService(restaurantRepository) }

    @Test
    fun testBalance() {
        val restaurantAccount = Account("restaurant-account", BigDecimal("4.5"), 6)
        entityManager.persist(restaurantAccount)
        entityManager.persist(Restaurant("restaurant", restaurantAccount))
        entityManager.flush()

        assertThat(service.balance("restaurant")).isEqualByComparingTo(BigDecimal("4.5"))
    }
}