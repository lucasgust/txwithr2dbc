package example.micronaut.txwithr2dbc.shared

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.LocalDateTime

abstract class Auditable {
    @DateCreated
    var createdAt: LocalDateTime? = null

    @DateUpdated
    var modifiedAt: LocalDateTime? = null
}
