package example.micronaut.txwithr2dbc.employee.ports.driver

import example.micronaut.txwithr2dbc.employee.model.Employee
import java.util.UUID

interface GetEmployeePort {
    suspend fun getById(id: UUID): Employee
}
