package joowon.study.kotlin.accounts

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
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

    @GetMapping
    fun getAccounts(pageable : Pageable, accountPagedResources : PagedResourcesAssembler<Account>) : ResponseEntity<Any> {
        val accounts = accountRepository.findAll(pageable)
        val pagedResource = accountPagedResources.toResource(accounts) {
                    val accountResource = AccountResource(it)
                    accountResource.add(linkTo(AccountController::class.java).slash(it.id).withSelfRel())
                    accountResource}
        return ResponseEntity.ok(pagedResource)
    }

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: Int) : ResponseEntity<Any> {

        //JpaRepository의 기본 구현체중 하나인 findById()는 return 타입이 optional<T>
        val optionalAccount = accountRepository.findById(id)

        //자바 1.8에서 추가된 optional을 사용하여 null 처리
        //여기서의 null 처리는 id에 해당하는 데이터가 없을 경우
        if (optionalAccount.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val account = optionalAccount.get()

        val accountResource = AccountResource(account)
        accountResource.add(linkTo(AccountController::class.java).slash(account.id).withSelfRel())
        accountResource.add(linkTo(AccountController::class.java).slash(account.id).withRel("patch-account"))
        accountResource.add(linkTo(AccountController::class.java).slash(account.id).withRel("delete-account"))

        return ResponseEntity.ok(accountResource)
    }

    @PatchMapping("/{id}")
    fun updatePasswordForAccount(@PathVariable id: Int, @RequestBody account: Account, errors: Errors): ResponseEntity<Any> {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors)
        }

        val optionalAccount = accountRepository.findById(id)

        if (optionalAccount.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val getAccount = optionalAccount.get()
        getAccount.password = account.password

        val updateAccount = accountRepository.save(getAccount)

        val accountResource = AccountResource(updateAccount)
        accountResource.add(linkTo(AccountController::class.java).slash(updateAccount.id).withSelfRel())
        accountResource.add(linkTo(AccountController::class.java).slash(updateAccount.id).withRel("patch-account"))
        accountResource.add(linkTo(AccountController::class.java).slash(updateAccount.id).withRel("delete-account"))
        return ResponseEntity.ok(accountResource)
    }

    @DeleteMapping("/{id}")
    fun deleteAccount(@PathVariable id: Int) : ResponseEntity<Any> {

        val optionalAccount = accountRepository.findById(id)

        if (optionalAccount.isEmpty) {
            return ResponseEntity.notFound().build()
        }

        val account = optionalAccount.get()
        accountRepository.deleteById(id)

        return ResponseEntity.status(HttpStatus.GONE).body(account)
    }

}