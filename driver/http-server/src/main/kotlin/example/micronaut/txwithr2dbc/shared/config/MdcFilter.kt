package example.micronaut.txwithr2dbc.shared.config

import example.micronaut.txwithr2dbc.shared.MdcPropagationContext
import example.micronaut.txwithr2dbc.shared.config.Headers.REQUEST_ID
import io.micronaut.core.propagation.MutablePropagatedContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.annotation.Filter.MATCH_ALL_PATTERN
import io.micronaut.http.annotation.RequestFilter
import io.micronaut.http.annotation.ServerFilter
import org.slf4j.MDC
import java.util.UUID


@ServerFilter(MATCH_ALL_PATTERN)
class MdcFilter {

    @RequestFilter
    fun myRequestFilter(request: HttpRequest<*>, mutablePropagatedContext: MutablePropagatedContext) {
        try {
            val requestId = request.headers.get(REQUEST_ID) ?: UUID.randomUUID().toString()
            val httpPath = request.path
            val httpMethod = request.method

            MDC.put("requestId", requestId)
            MDC.put("httpPath", httpPath)
            MDC.put("httpMethod", httpMethod.toString())

            mutablePropagatedContext.add(MdcPropagationContext())
        } finally {
            MDC.remove("requestId")
            MDC.remove("httpPath")
            MDC.remove("httpMethod")
        }
    }
}