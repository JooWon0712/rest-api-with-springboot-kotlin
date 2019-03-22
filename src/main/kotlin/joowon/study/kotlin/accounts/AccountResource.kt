package joowon.study.kotlin.accounts

import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-22
 * Time: 22:37
 */
class AccountResource(content: Account?, vararg links: Link?) : Resource<Account>(content, *links) {

}