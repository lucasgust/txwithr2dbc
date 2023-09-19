package example.micronaut.txwithr2dbc

import example.micronaut.txwithr2dbc.employee.entity.EmployeeEntity
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.repository.EmployeeWriteRepository
import example.micronaut.txwithr2dbc.shared.IntegrationTestCase
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus.BAD_REQUEST
import io.micronaut.http.HttpStatus.OK
import io.restassured.http.ContentType.JSON
import jakarta.inject.Inject
import org.instancio.Instancio.create
import java.util.UUID

class GetEmployeeIntegrationTest : IntegrationTestCase() {

    @Inject
    lateinit var employeeWriteRepository: EmployeeWriteRepository

    init {
        should("get an employee") {
            val employee = create(Employee::class.java).also {
                transactional { employeeWriteRepository.save(it.toEntity()) }
            }

            val actual = spec
                .given()
                .contentType(JSON)
                .get("/employees/${employee.id}")
                .then()
                .statusCode(OK.code)
                .extract()
                .body()
                .`as`(Employee::class.java)

            actual shouldBe employee
        }

        should("not get an employee") {
            spec
                .given()
                .contentType(JSON)
                .get("/employees/${UUID.randomUUID()}")
                .then()
                .statusCode(BAD_REQUEST.code)
        }
    }

    private fun Employee.toEntity() = EmployeeEntity(id, name)
}
