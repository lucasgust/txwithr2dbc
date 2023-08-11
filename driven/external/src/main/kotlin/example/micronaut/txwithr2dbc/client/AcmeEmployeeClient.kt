package example.micronaut.txwithr2dbc.client

import example.micronaut.txwithr2dbc.dto.AcmeEmployeeDTO
import example.micronaut.txwithr2dbc.dto.AcmeEmployeeResponseDTO
import io.micronaut.http.HttpHeaders.ACCEPT
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Headers
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import reactor.core.publisher.Mono

@Client(value = "http://localhost:9561")
@Headers(Header(name = ACCEPT, value = APPLICATION_JSON))
interface AcmeEmployeeClient {

    @Post(value = "/employee")
    fun create(@Body employee: AcmeEmployeeDTO): Mono<HttpResponse<AcmeEmployeeResponseDTO>>
}
