package example.micronaut.txwithr2dbc.employee.ports.driven

import example.micronaut.txwithr2dbc.employee.model.Employee
import java.util.UUID

interface EmployeeDataAccessPort {

    suspend fun save(employee: Employee): Employee

    suspend fun findById(id: UUID): Employee?

    suspend fun findAll(): List<Employee>
}
