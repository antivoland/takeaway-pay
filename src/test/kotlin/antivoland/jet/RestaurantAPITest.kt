package antivoland.jet

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class RestaurantAPITest(@Autowired val api: MockMvc) {
    @Test
    fun balance() {
        val id = "🍄"
        api.perform(get("/restaurants/$id/balance")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("Not implemented yet"))
    }
}