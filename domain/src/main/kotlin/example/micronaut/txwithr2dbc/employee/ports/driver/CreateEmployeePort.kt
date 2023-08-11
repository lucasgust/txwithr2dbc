package example.micronaut.txwithr2dbc.employee.ports.driver

import example.micronaut.txwithr2dbc.employee.model.Employee

interface CreateEmployeePort {
    suspend fun create(employee: Employee): Employee
}
