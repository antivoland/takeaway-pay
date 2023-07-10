package antivoland.jet.api

import antivoland.jet.exception.CustomerHasInsufficientFundsException
import antivoland.jet.exception.CustomerNotFoundException
import antivoland.jet.exception.RestaurantNotFoundException
import antivoland.jet.service.CustomerService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@WebMvcTest(CustomerAPI::class)
class CustomerAPITest(@Autowired val api: MockMvc) {
    @MockkBean
    lateinit var service: CustomerService

    @Test
    fun testBalance() {
        every { service.balance(any()) } returns BigDecimal("1675.1749")
        val request = get("/customers/william-jones/balance")
        api.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().string("1675.1749"))
    }

    @Test
    fun testBalanceWhenCustomerNotFound() {
        every { service.balance(any()) }.throws(CustomerNotFoundException("william-jones"))
        val request = get("/customers/william-jones/balance")
        api.perform(request)
            .andExpect(status().isNotFound)
            .andExpect(content().string("Customer 'william-jones' not found"))
    }

    @Test
    fun testPay() {
        every { service.pay(any(), any()) } returns Unit
        val request = post("/customers/william-jones/pay")
            .content(order())
            .contentType(APPLICATION_JSON)
        api.perform(request)
            .andExpect(status().isCreated)
    }

    @Test
    fun testPayWhenCustomerNotFound() {
        every { service.pay(any(), any()) }.throws(CustomerNotFoundException("william-jones"))
        val request = post("/customers/william-jones/pay")
            .content(order())
            .contentType(APPLICATION_JSON)
        api.perform(request)
            .andExpect(status().isNotFound)
            .andExpect(content().string("Customer 'william-jones' not found"))
    }

    @Test
    fun testPayWhenRestaurantNotFound() {
        every { service.pay(any(), any()) }.throws(RestaurantNotFoundException("pi"))
        val request = post("/customers/william-jones/pay")
            .content(order())
            .contentType(APPLICATION_JSON)
        api.perform(request)
            .andExpect(status().isNotFound)
            .andExpect(content().string("Restaurant 'pi' not found"))
    }

    @Test
    fun testPayCustomerHasInsufficientFunds() {
        every { service.pay(any(), any()) }.throws(CustomerHasInsufficientFundsException("william-jones"))
        val request = post("/customers/william-jones/pay")
            .content(order())
            .contentType(APPLICATION_JSON)
        api.perform(request)
            .andExpect(status().isBadRequest)
            .andExpect(content().string("Customer 'william-jones' has insufficient funds"))
    }

    fun order() =
        """
        {
            "restaurant_id": "pi",
            "amount": 3.1415
        }
        """
}