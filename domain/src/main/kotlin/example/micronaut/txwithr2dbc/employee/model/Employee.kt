package example.micronaut.txwithr2dbc.employee.model

import java.util.UUID

data class Employee(
    val id: UUID,
    val name: String,
)