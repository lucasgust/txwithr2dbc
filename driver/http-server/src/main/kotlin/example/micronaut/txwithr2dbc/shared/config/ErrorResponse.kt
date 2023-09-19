package example.micronaut.txwithr2dbc.shared.config

import example.micronaut.txwithr2dbc.shared.AppException
import io.micronaut.http.HttpRequest

data class ErrorResponse(
    val message: String,
    val errorCode: String,
    val parameters: Map<String, Any?>
)

fun AppException.toErrorResponse() = ErrorResponse(
    message = message,
    errorCode = errorCode,
    parameters = parameters
)

fun Throwable.toErrorResponse(request: HttpRequest<*>?) = ErrorResponse(
    message = "An unexpected error occurred while processing request",
    errorCode = "UNHANDLED_ERROR",
    parameters = mapOf(
        "method" to request?.methodName,
        "path" to request?.path,
        "params" to request?.parameters,
        "errorMessage" to (message ?: this::class.java)
    )
)