package example.micronaut.txwithr2dbc.employee.usecase

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driver.ListEmployeePort
import example.micronaut.txwithr2dbc.employee.service.ListEmployeeService
import jakarta.inject.Singleton

@Singleton
open class ListEmployeeUseCase(
    private val listEmployeeService: ListEmployeeService
) : ListEmployeePort {

    override suspend fun list(): List<Employee> {
        return listEmployeeService.list()
    }
}
