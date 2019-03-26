package joowon.study.kotlin.accounts

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.MediaTypes
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
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

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Before
    fun testSetup() {
        val account1 = Account(null, "admin1@admin.com", "1234")
        val account2 = Account(null, "admin2@admin.com", "1234")
        val account3 = Account(null, "admin3@admin.com", "1234")
        val account4 = Account(null, "admin4@admin.com", "1234")
        val account5 = Account(null, "admin5@admin.com", "1234")
        accountRepository.save(account1)
        accountRepository.save(account2)
        accountRepository.save(account3)
        accountRepository.save(account4)
        accountRepository.save(account5)
    }

    @Test
    fun testCreateAccount() {

        //given
        val accountJson = "{\"email\" : \"test@test.com\", \"password\" : \"1234\"}"

        //when
        mockMvc.perform(post("/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(accountJson))
                .andDo(print())
                //then
                .andExpect(status().isCreated)
                .andExpect(jsonPath("links[0].rel").value("self"))
    }

    @Test
    fun testCreateAccount_badRequest() {

        //given
        val accountJson = "{\"email\" : \"test@test.com\"}"

        //when
        mockMvc.perform(post("/api/accounts")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(accountJson))
                .andDo(print())
                //then
                .andExpect(status().isBadRequest)
    }

    @Test
    fun getAccount() {

        //given
        val id = 1

        //when
        mockMvc.perform(get("/api/accounts/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                //then
                .andExpect(status().isOk)
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("links[0].rel").value("self"))
    }

    @Test
    fun getAccount_fail() {

        //given
        val id = 2

        //when
        mockMvc.perform(get("/api/accounts/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                //then
                .andExpect(status().isNotFound)
    }

    @Test
    fun updatePasswordForAccount() {

        //given
        val accountJson = "{\"email\" : \"admin@admin.com\", \"password\" : \"1234\"}"
        val id = 1

        //when
        mockMvc.perform(patch("/api/accounts/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON)
                    .content(accountJson))
                .andDo(print())
                //then
                .andExpect(status().isOk)
    }

    @Test
    fun deleteAccount() {
        //given
        val id = 1

        //when
        mockMvc.perform(delete("/api/accounts/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                //then
                .andExpect(status().isGone)
    }

    @Test
    fun getAccounts() {

        //when
        mockMvc.perform(get("/api/accounts")
                    //given
                    .param("page", "1")
                    .param("size", "2")
                    .param("sort", "email,DESC"))
                //then
                .andDo(print())
                .andExpect(status().isOk)
    }
}