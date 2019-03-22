package joowon.study.kotlin.accounts

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-22
 * Time: 18:03
 */
@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByEmail(email: String) : Account?
}