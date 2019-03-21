package joowon.study.kotlin.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-21
 * Time: 19:11
 */

@RestController
class HelloController {

    @GetMapping("/hello")
    fun helloKotlin() : String {
        return "Hello Kotlin"
    }

}