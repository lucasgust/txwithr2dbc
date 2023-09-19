package example.micronaut.txwithr2dbc.employee.usecase

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driver.GetEmployeePort
import example.micronaut.txwithr2dbc.employee.service.GetEmployeeService
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
open class GetEmployeeUseCase(
    private val getEmployeeService: GetEmployeeService
) : GetEmployeePort {

    override suspend fun getById(id: UUID): Employee {
        return getEmployeeService.getById(id)
    }
}
