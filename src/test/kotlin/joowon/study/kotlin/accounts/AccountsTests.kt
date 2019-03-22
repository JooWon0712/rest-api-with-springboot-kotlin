package joowon.study.kotlin.accounts

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-22
 * Time: 17:49
 */
@RunWith(SpringRunner::class)
@DataJpaTest
class AccountsTests {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    fun testAccount() {
        val account = Account(null, "test@test.com", "test")
        println(account)
    }

    @Test
    fun testAccountSave() {
        val account = Account(null, "test@test.com", "test")
        accountRepository.save(account)
        accountRepository.findAll()
    }

}