package example.micronaut.txwithr2dbc.employee.service

import example.micronaut.txwithr2dbc.employee.ports.driven.EmployeeDataAccessPort
import jakarta.inject.Singleton

@Singleton
open class ListEmployeeService(
    private val employeeDataAccessPort: EmployeeDataAccessPort
) {
    suspend fun list() = employeeDataAccessPort.findAll()
}
