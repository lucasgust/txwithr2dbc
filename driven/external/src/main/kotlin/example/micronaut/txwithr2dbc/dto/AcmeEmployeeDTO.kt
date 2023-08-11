package example.micronaut.txwithr2dbc.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import example.micronaut.txwithr2dbc.employee.model.Employee

@JsonNaming(SnakeCaseStrategy::class)
data class AcmeEmployeeDTO(
    val id: String,
    val name: String,
)

fun Employee.toAcmeEmployeeDTO() = AcmeEmployeeDTO(
    id = id.toString(),
    name = name,
)
