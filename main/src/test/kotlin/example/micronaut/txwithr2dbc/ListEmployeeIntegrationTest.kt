package example.micronaut.txwithr2dbc

import example.micronaut.txwithr2dbc.employee.entity.EmployeeEntity
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.repository.EmployeeWriteRepository
import example.micronaut.txwithr2dbc.shared.IntegrationTestCase
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus.OK
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType.JSON
import jakarta.inject.Inject
import org.instancio.Instancio.ofList

class ListEmployeeIntegrationTest : IntegrationTestCase() {

    @Inject
    lateinit var employeeWriteRepository: EmployeeWriteRepository

    init {
        should("list employees") {
            val employees = ofList(Employee::class.java).create().also {
                transactional { it.forEach { e -> employeeWriteRepository.save(e.toEntity()) } }
            }

            val actual = spec
                .given()
                .contentType(JSON)
                .get("/employees")
                .then()
                .statusCode(OK.code)
                .extract()
                .body()
                .`as`(object : TypeRef<List<Employee>>() {})

            actual shouldContainExactlyInAnyOrder employees
        }

        should("not list employees") {
            val actual = spec
                .given()
                .contentType(JSON)
                .get("/employees")
                .then()
                .statusCode(OK.code)
                .extract()
                .body()
                .`as`(object : TypeRef<List<Employee>>() {})

            actual shouldBe emptyList()
        }
    }

    private fun Employee.toEntity() = EmployeeEntity(id, name)
}
