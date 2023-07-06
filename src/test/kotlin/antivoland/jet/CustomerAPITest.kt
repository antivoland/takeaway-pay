package antivoland.jet

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class CustomerAPITest(@Autowired val api: MockMvc) {
    @Test
    fun balance() {
        val id = "🐶"
        api.perform(get("/customers/$id/balance")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("Not implemented yet"))
    }

    @Test
    fun pay() {
        val id = "🐶"
        api.perform(post("/customers/$id/pay")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("Not implemented yet"))
    }
}