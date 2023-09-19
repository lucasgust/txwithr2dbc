package example.micronaut.txwithr2dbc.employee.service

import example.micronaut.txwithr2dbc.employee.ports.driven.EmployeeDataAccessPort
import example.micronaut.txwithr2dbc.shared.AppException.BusinessException
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
open class GetEmployeeService(
    private val employeeDataAccessPort: EmployeeDataAccessPort
) {
    suspend fun getById(id: UUID) = employeeDataAccessPort.findById(id)
        ?: throw BusinessException(
            message = "employee not found",
            errorCode = "EMPLOYEE_NOT_FOUND",
            parameters = mapOf("id" to id)
        )
}
