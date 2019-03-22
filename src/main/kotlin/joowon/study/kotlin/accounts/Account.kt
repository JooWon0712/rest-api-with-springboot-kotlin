package joowon.study.kotlin.accounts

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Created by JooWon0712.
 * User: joowon
 * Date: 2019-03-22
 * Time: 17:45
 */
@Entity
data class Account(
    @Id
    @GeneratedValue
    var id: Int? = null,

    @Column(unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @CreationTimestamp
    var createDate: LocalDateTime? = null,

    @UpdateTimestamp
    var updateDate: LocalDateTime? = null
)