package example.micronaut.txwithr2dbc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import example.micronaut.txwithr2dbc.dto.AcmeEmployeeResponseDTO
import example.micronaut.txwithr2dbc.employee.ports.driven.EmployeeDataAccessPort
import example.micronaut.txwithr2dbc.externalemployee.ports.driven.ExternalEmployeeDataAccessPort
import example.micronaut.txwithr2dbc.utils.WireMockUtils.withStub
import io.kotest.core.spec.IsolationMode.InstancePerLeaf
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.micronaut.data.r2dbc.operations.R2dbcOperations
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.instancio.Instancio.create
import org.junit.jupiter.api.Assertions.assertEquals
import reactor.kotlin.core.publisher.toMono

@MicronautTest(transactional = false)
class EmployeeIntegrationTest : ShouldSpec() {

    @Inject
    lateinit var operations: R2dbcOperations

    @Inject
    lateinit var employeeController: EmployeeController

    @Inject
    lateinit var employeeDataAccessPort: EmployeeDataAccessPort

    @Inject
    lateinit var externalEmployeeDataAccessPort: ExternalEmployeeDataAccessPort

    private val wireMockServer = WireMockServer(9561)

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        wireMockServer.start()
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        wireMockServer.stop()
    }

    init {
        isolationMode = InstancePerLeaf

        beforeEach {
            operations.withTransaction {
                runBlocking { employeeDataAccessPort.deleteAll().toMono() }
            }
        }

        should("insert an employee") {
            wireMockServer.withStub(
                method = HttpMethod.POST,
                url = "/employee",
                httpStatus = HttpStatus.OK,
                responseBody = jacksonObjectMapper().writeValueAsString(create(AcmeEmployeeResponseDTO::class.java))
            )

            operations.withTransaction {
                runBlocking { employeeController.create().toMono() }
            }

            assertEquals(1L, employeeDataAccessPort.count())
            assertEquals(1L, externalEmployeeDataAccessPort.count())
        }

        should("insert only four employees") {
            wireMockServer.withStub(
                method = HttpMethod.POST,
                url = "/employee",
                httpStatus = HttpStatus.OK,
                responseBody = jacksonObjectMapper().writeValueAsString(create(AcmeEmployeeResponseDTO::class.java))
            )

            // simulating an error in AcmeAdapter every 5 times...
            repeat(5) {
                operations.withTransaction {
                    runBlocking { employeeController.create().toMono() }
                }
            }

            assertEquals(4L, employeeDataAccessPort.count())
            assertEquals(4L, externalEmployeeDataAccessPort.count())
        }
    }
}
