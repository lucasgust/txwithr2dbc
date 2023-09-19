package example.micronaut.txwithr2dbc.shared.config

import example.micronaut.txwithr2dbc.shared.config.Headers.REQUEST_ID
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import org.reactivestreams.Publisher
import org.slf4j.MDC
import reactor.core.publisher.Mono
import java.util.UUID

@Filter("/**")
class RequestMDC : HttpServerFilter {
    override fun doFilter(
        request: HttpRequest<*>?,
        chain: ServerFilterChain
    ): Publisher<MutableHttpResponse<*>> {
        if (request != null) {
            val requestId = request.headers.get(REQUEST_ID) ?: UUID.randomUUID().toString()
            val httpPath = request.path
            val httpMethod = request.method

            MDC.put("requestId", requestId)
            MDC.put("httpPath", httpPath)
            MDC.put("httpMethod", httpMethod.toString())
        }

        return Mono.from(chain.proceed(request))
    }
}
