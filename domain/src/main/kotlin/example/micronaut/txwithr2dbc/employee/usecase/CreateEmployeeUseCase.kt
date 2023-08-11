package example.micronaut.txwithr2dbc.employee.usecase

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driver.CreateEmployeePort
import example.micronaut.txwithr2dbc.employee.service.CreateEmployeeService
import example.micronaut.txwithr2dbc.externalemployee.service.CreateExternalEmployeeService
import io.micronaut.transaction.annotation.Transactional
import jakarta.inject.Singleton

@Singleton
open class CreateEmployeeUseCase(
    private val createEmployeeService: CreateEmployeeService,
    private val createExternalEmployeeService: CreateExternalEmployeeService
) : CreateEmployeePort {

    @Transactional
    override suspend fun create(employee: Employee) =
        createEmployeeService.create(employee)
            .also { createExternalEmployeeService.create(it) }
}
