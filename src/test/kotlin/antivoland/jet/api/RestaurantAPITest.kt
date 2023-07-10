package antivoland.jet.api

import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.RestaurantService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.math.BigDecimal

@WebMvcTest(RestaurantAPI::class)
class RestaurantAPITest(@Autowired val api: MockMvc) {
    @MockkBean
    lateinit var service: RestaurantService

    @Test
    fun testBalance() {
        every { service.balance(any()) } returns BigDecimal("3.1415")
        api.perform(get("/restaurants/pi/balance"))
            .andExpect(status().isOk)
            .andExpect(content().string("3.1415"))
    }

    @Test
    fun testBalanceWhenRestaurantNotFound() {
        every { service.balance(any()) }.throws(RestaurantNotFoundException("pi"))
        api.perform(get("/restaurants/pi/balance"))
            .andExpect(status().isNotFound)
            .andExpect(content().string("Restaurant 'pi' not found"))
    }
}