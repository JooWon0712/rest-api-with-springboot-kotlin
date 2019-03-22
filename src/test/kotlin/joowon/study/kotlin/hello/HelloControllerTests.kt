package joowon.study.kotlin.hello

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-21
 * Time: 21:43
 */

@RunWith(SpringRunner::class)
@WebMvcTest(HelloController::class)
class HelloControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testHello() {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk)
    }

}