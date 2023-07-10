package antivoland.jet.service

import antivoland.jet.repository.RestaurantRepository
import antivoland.jet.repository.entity.Account
import antivoland.jet.repository.entity.Restaurant
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset.offset
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RestaurantServiceTest(
    @Autowired val restaurantRepository: RestaurantRepository,
    @Autowired val entityManager: TestEntityManager
) {
    val service by lazy { RestaurantService(restaurantRepository) }

    @Test
    fun testBalance() {
        entityManager.persistAndFlush(account())
        entityManager.persistAndFlush(restaurant())
        assertThat(service.balance("restaurant"))
            .isCloseTo(1.2, offset(0.01))
    }

    fun restaurant() = Restaurant("restaurant", account())

    fun account() = Account("account", 1.2, 3)
}