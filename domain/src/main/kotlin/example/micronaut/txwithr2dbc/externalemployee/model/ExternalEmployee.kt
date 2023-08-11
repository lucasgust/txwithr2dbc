package example.micronaut.txwithr2dbc.externalemployee.model

import example.micronaut.txwithr2dbc.employee.model.Employee

data class ExternalEmployee(
    val employee: Employee,
    val companyName: String,
)