package example.micronaut.txwithr2dbc.employee.usecase

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driver.CreateEmployeePort
import example.micronaut.txwithr2dbc.employee.service.CreateEmployeeService
import example.micronaut.txwithr2dbc.externalemployee.service.CreateExternalEmployeeService
import example.micronaut.txwithr2dbc.shared.TransactionPort
import jakarta.inject.Singleton

@Singleton
open class CreateEmployeeUseCase(
    private val transactional: TransactionPort,
    private val createEmployeeService: CreateEmployeeService,
    private val createExternalEmployeeService: CreateExternalEmployeeService
) : CreateEmployeePort {

    override suspend fun create(employee: Employee) = transactional {
        createEmployeeService.create(employee)
            .also { createExternalEmployeeService.create(it) }
    }
}
