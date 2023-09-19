package example.micronaut.txwithr2dbc.shared

sealed class AppException(
    override val message: String,
    open val parameters: Map<String, Any?> = emptyMap()
) : RuntimeException(message) {

    abstract val errorCode: String

    open class BusinessException(
        override val message: String,
        override val errorCode: String,
        override val parameters: Map<String, Any?> = emptyMap()
    ) : AppException(message, parameters)

    open class GeneralException(
        override val message: String,
        override val errorCode: String,
        override val parameters: Map<String, Any?> = emptyMap()
    ) : AppException(message, parameters)
}
