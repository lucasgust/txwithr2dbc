package example.micronaut.txwithr2dbc.shared.config

import example.micronaut.txwithr2dbc.shared.AppException
import io.micronaut.context.annotation.Requirements
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponseFactory
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
@Requirements(
    Requires(
        classes = [
            ExceptionHandler::class,
            Throwable::class
        ]
    )
)
class GlobalThrowableExceptionHandler : ExceptionHandler<Throwable, HttpResponse<*>> {
    override fun handle(request: HttpRequest<*>?, exception: Throwable?): HttpResponse<*> {
        return when (exception) {
            is AppException -> handleAppException(exception)
            else -> handleServerErrorException(request, exception)
        }
    }

    private fun handleAppException(exception: AppException): HttpResponse<*> {
        logger.error(exception.message, exception)
        return HttpResponseFactory.INSTANCE.status(exception.httpStatus(), exception.toErrorResponse())
    }

    private fun AppException.httpStatus() = when (this) {
        is AppException.BusinessException -> HttpStatus.BAD_REQUEST
        is AppException.GeneralException -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    private fun handleServerErrorException(
        request: HttpRequest<*>?,
        exception: Throwable?
    ): HttpResponse<*> {
        return exception?.toErrorResponse(request).let {
            logger.error(it?.message ?: "unhandled error", exception)
            HttpResponse.serverError(it)
        } ?: HttpResponse.serverError("unhandled error")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GlobalThrowableExceptionHandler::class.java)
    }
}
