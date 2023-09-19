package example.micronaut.txwithr2dbc.shared

import com.github.tomakehurst.wiremock.WireMockServer
import io.kotest.core.spec.IsolationMode.SingleInstance
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.restassured.specification.RequestSpecification
import jakarta.inject.Inject

@MicronautTest(transactional = false)
abstract class IntegrationTestCase : ShouldSpec() {

    @Inject
    private lateinit var databaseCleanUpService: DatabaseCleanUpService

    @Inject
    private lateinit var databasePopulatorService: DatabasePopulatorService

    @Inject
    protected lateinit var transactional: TransactionPort

    @Inject
    protected lateinit var spec: RequestSpecification

    protected val wireMockServer = WireMockServer(9561)

    override fun isolationMode() = SingleInstance

    override suspend fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        wireMockServer.start()
    }

    override suspend fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)
        wireMockServer.stop()
    }

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        super.afterEach(testCase, result)
        databaseCleanUpService()
    }

    protected fun populateDatabase(vararg sqlFiles: String) {
        sqlFiles.forEach { databasePopulatorService(it) }
    }
}
