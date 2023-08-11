package example.micronaut.txwithr2dbc.employee.service

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driven.EmployeeDataAccessPort
import jakarta.inject.Singleton

@Singleton
open class CreateEmployeeService(
    private val employeeDataAccessPort: EmployeeDataAccessPort
) {
    suspend fun create(employee: Employee) = employeeDataAccessPort.save(employee)
}
