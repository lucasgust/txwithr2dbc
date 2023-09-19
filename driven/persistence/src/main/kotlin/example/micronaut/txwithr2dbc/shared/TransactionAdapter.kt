package example.micronaut.txwithr2dbc.shared

import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Singleton

@Singleton
open class TransactionAdapter : TransactionPort {

    @Transactional
    override suspend operator fun <T> invoke(block: suspend () -> T) = block.invoke()
}