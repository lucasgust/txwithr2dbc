package example.micronaut.txwithr2dbc.shared

import io.micronaut.data.repository.jpa.kotlin.CoroutineJpaSpecificationExecutor
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository

sealed interface Repository<T, ID> : CoroutineCrudRepository<T, ID>, CoroutineJpaSpecificationExecutor<T> {

    interface WriteRepository<T, ID> : Repository<T, ID>

    interface ReadRepository<T, ID> : Repository<T, ID>
}