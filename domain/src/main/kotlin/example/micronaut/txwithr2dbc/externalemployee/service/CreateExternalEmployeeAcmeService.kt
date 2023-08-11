package example.micronaut.txwithr2dbc.externalemployee.service

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee
import example.micronaut.txwithr2dbc.externalemployee.ports.driven.ExternalEmployeeApiPort
import example.micronaut.txwithr2dbc.externalemployee.ports.driven.ExternalEmployeeDataAccessPort
import jakarta.inject.Singleton

@Singleton
class CreateExternalEmployeeAcmeService(
    private val externalEmployeeDataAccessPort: ExternalEmployeeDataAccessPort,
    private val externalEmployeeApiPort: ExternalEmployeeApiPort
) : CreateExternalEmployeeService {

    override suspend fun create(employee: Employee): ExternalEmployee {
        return externalEmployeeApiPort.create(employee)
            .let { externalEmployeeDataAccessPort.save(it) }
    }
}
