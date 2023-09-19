package example.micronaut.txwithr2dbc

import example.micronaut.txwithr2dbc.dto.AcmeEmployeeResponseDTO
import example.micronaut.txwithr2dbc.employee.entity.EmployeeEntity
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.repository.EmployeeReadRepository
import example.micronaut.txwithr2dbc.externalemployee.entity.ExternalEmployeeId
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee
import example.micronaut.txwithr2dbc.externalemployee.repository.ExternalEmployeeReadRepository
import example.micronaut.txwithr2dbc.shared.IntegrationTestCase
import example.micronaut.txwithr2dbc.shared.ObjectMapperProvider.mapper
import example.micronaut.txwithr2dbc.shared.WireMockUtils.withStub
import example.micronaut.txwithr2dbc.shared.config.ErrorResponse
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.http.HttpMethod.POST
import io.micronaut.http.HttpStatus.ALREADY_IMPORTED
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.HttpStatus.INTERNAL_SERVER_ERROR
import io.micronaut.http.HttpStatus.OK
import io.restassured.http.ContentType.JSON
import jakarta.inject.Inject
import org.instancio.Instancio.create

class CreateEmployeeIntegrationTest : IntegrationTestCase() {

    @Inject
    lateinit var employeeReadRepository: EmployeeReadRepository

    @Inject
    lateinit var externalEmployeeReadRepository: ExternalEmployeeReadRepository

    init {
        should("insert an employee") {
            val employee = create(Employee::class.java)
            val externalEmployee = employee.toExternalEmployee()

            wireMockServer.withStub(
                method = POST,
                url = "/employee",
                httpStatus = CREATED,
                responseBody = mapper.writeValueAsString(externalEmployee.toExternalResponseDTO())
            )

            val actual = spec
                .given()
                .contentType(JSON)
                .body(mapper.writeValueAsString(employee))
                .post("/employees")
                .then()
                .statusCode(OK.code)
                .extract()
                .body()
                .`as`(Employee::class.java)

            actual shouldBe employee

            employeeReadRepository.findById(actual.id)!!
                .toModel() shouldBe employee

            externalEmployeeReadRepository.findById(externalEmployee.toEmbeddedId(employee))!!
                .toModel() shouldBe externalEmployee
        }

        should("not insert an employee") {
            val employee = create(Employee::class.java)
            val externalEmployee = employee.toExternalEmployee()

            wireMockServer.withStub(
                method = POST,
                url = "/employee",
                httpStatus = ALREADY_IMPORTED,
                responseBody = ""
            )

            val actual = spec
                .given()
                .contentType(JSON)
                .body(mapper.writeValueAsString(employee))
                .post("/employees")
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.code)
                .extract()
                .body()
                .`as`(ErrorResponse::class.java)

            actual shouldNotBe null

            employeeReadRepository.findById(employee.id) shouldBe null

            externalEmployeeReadRepository.findById(externalEmployee.toEmbeddedId(employee)) shouldBe null
        }
    }

    private val acmeCompanyName = "ACME"

    private fun Employee.toExternalEmployee() = ExternalEmployee(employee = this, companyName = acmeCompanyName)
    private fun Employee.toEntity() = EmployeeEntity(id, name)

    private fun ExternalEmployee.toExternalResponseDTO() = AcmeEmployeeResponseDTO(employee.id, companyName)

    private fun ExternalEmployee.toEmbeddedId(employee: Employee) =
        ExternalEmployeeId(employee.toEntity(), companyName)
}
