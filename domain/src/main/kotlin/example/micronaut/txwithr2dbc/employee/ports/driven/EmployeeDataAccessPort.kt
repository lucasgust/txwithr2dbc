package example.micronaut.txwithr2dbc.employee.ports.driven

import example.micronaut.txwithr2dbc.employee.model.Employee

interface EmployeeDataAccessPort {

    suspend fun save(employee: Employee): Employee

    suspend fun deleteAll()

    suspend fun count(): Long

    suspend fun findAll(): List<Employee>
}
