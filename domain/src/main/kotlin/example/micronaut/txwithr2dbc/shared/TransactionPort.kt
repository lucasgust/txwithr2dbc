package example.micronaut.txwithr2dbc.shared

interface TransactionPort {

    suspend operator fun <T> invoke(block: suspend () -> T) = block.invoke()
}