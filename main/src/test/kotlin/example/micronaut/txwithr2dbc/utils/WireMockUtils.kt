package example.micronaut.txwithr2dbc.utils

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import io.micronaut.http.HttpHeaders.CONTENT_TYPE
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpMethod.GET
import io.micronaut.http.HttpMethod.POST
import io.micronaut.http.HttpMethod.PUT
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType.APPLICATION_JSON

object WireMockUtils {

    fun WireMockServer.withStub(
        method: HttpMethod,
        url: String,
        httpStatus: HttpStatus,
        responseBody: String
    ) {
        stubFor(
            when (method) {
                GET -> get(urlEqualTo(url))
                POST -> post(urlEqualTo(url))
                PUT -> put(urlEqualTo(url))
                else -> error("implement me!!!")
            }.willReturn(
                wireMockResponseBody(httpStatus, responseBody)
            )
        )
    }

    private fun wireMockResponseBody(
        httpStatus: HttpStatus,
        responseBody: String
    ) = aResponse()
        .withStatus(httpStatus.code)
        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
        .withBody(responseBody)
}
