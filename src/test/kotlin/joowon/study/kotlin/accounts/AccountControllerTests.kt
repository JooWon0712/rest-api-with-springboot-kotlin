package joowon.study.kotlin.accounts

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-22
 * Time: 21:05
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTests{

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testCreateAccount() {

        val accountJson = "{\"email\" : \"test@test.com\", \"password\" : \"1234\"}"

        mockMvc.perform(post("/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(accountJson))
                .andDo(print())
                .andExpect(status().isCreated)
                .andExpect(jsonPath("links[0].rel").value("self"))
    }

    @Test
    fun testCreateAccount_badRequest() {

        val accountJson = "{\"email\" : \"test@test.com\"}"

        mockMvc.perform(post("/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(accountJson))
                .andDo(print())
                .andExpect(status().isBadRequest)
    }
}