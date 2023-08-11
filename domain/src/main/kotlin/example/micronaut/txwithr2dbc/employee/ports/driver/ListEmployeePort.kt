package example.micronaut.txwithr2dbc.employee.ports.driver

import example.micronaut.txwithr2dbc.employee.model.Employee

interface ListEmployeePort {
    suspend fun list(): List<Employee>
}
