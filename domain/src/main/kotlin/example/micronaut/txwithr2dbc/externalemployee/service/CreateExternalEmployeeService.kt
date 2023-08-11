package example.micronaut.txwithr2dbc.externalemployee.service

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee

interface CreateExternalEmployeeService {
    suspend fun create(employee: Employee): ExternalEmployee
}
