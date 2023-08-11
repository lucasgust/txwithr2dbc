package example.micronaut.txwithr2dbc.employee.adapter

import example.micronaut.txwithr2dbc.employee.entity.toEntity
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driven.EmployeeDataAccessPort
import example.micronaut.txwithr2dbc.employee.repository.EmployeeReadRepository
import example.micronaut.txwithr2dbc.employee.repository.EmployeeWriteRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList

@Singleton
open class EmployeeDataAccessAdapter(
    private val employeeReadRepository: EmployeeReadRepository,
    private val employeeWriteRepository: EmployeeWriteRepository
) : EmployeeDataAccessPort {

    override suspend fun save(employee: Employee): Employee {
        return employeeWriteRepository.save(employee.toEntity()).toModel()
    }

    override suspend fun deleteAll() {
        employeeWriteRepository.deleteAll()
    }

    override suspend fun count(): Long {
        return employeeReadRepository.count()
    }

    override suspend fun findAll(): List<Employee> {
        return employeeReadRepository.findAll().toList().map { it.toModel() }
    }
}
