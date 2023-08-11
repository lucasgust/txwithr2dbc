package example.micronaut.txwithr2dbc.externalemployee.ports.driven

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee

interface ExternalEmployeeApiPort {
    suspend fun create(employee: Employee): ExternalEmployee
}
