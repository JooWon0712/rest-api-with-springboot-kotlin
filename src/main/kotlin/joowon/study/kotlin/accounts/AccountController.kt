package joowon.study.kotlin.accounts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import javax.validation.Valid

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-22
 * Time: 20:24
 */

@Controller
@RequestMapping("/api/accounts", produces = [MediaTypes.HAL_JSON_UTF8_VALUE])
class AccountController {

    @Autowired
    lateinit var accountRepository: AccountRepository

    @PostMapping
    fun createAccount(@RequestBody @Valid account: Account, errors: Errors) : ResponseEntity<Any> {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.toString())
        }
        val newAccount = accountRepository.save(account)

        val controllerLinkBuilder = linkTo(AccountController::class.java).slash(newAccount.id)
        val createUri = controllerLinkBuilder.toUri()

        val accountResource = AccountResource(newAccount)
        accountResource.add(linkTo(AccountController::class.java).slash(newAccount.id).withSelfRel())
        accountResource.add(linkTo(AccountController::class.java).slash(newAccount.id).withRel("patch-account"))
        accountResource.add(linkTo(AccountController::class.java).slash(newAccount.id).withRel("delete-account"))

        return ResponseEntity.created(createUri).body(accountResource)
    }
}
