package example.micronaut.txwithr2dbc.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee
import java.util.UUID

@JsonNaming(SnakeCaseStrategy::class)
data class AcmeEmployeeResponseDTO(
    val id: UUID,
    val name: String,
) {
    fun toModel() = ExternalEmployee(
        employee = toEmployee(),
        companyName = "ACME",
    )

    private fun toEmployee() = Employee(
        id = id,
        name = name,
    )
}