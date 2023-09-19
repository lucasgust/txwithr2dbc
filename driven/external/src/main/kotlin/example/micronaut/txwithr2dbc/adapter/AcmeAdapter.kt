package example.micronaut.txwithr2dbc.adapter

import example.micronaut.txwithr2dbc.client.AcmeEmployeeClient
import example.micronaut.txwithr2dbc.dto.toAcmeEmployeeDTO
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee
import example.micronaut.txwithr2dbc.externalemployee.ports.driven.ExternalEmployeeApiPort
import example.micronaut.txwithr2dbc.shared.AppException.GeneralException
import jakarta.inject.Singleton
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull

@Singleton
class AcmeAdapter(
    private val acmeClient: AcmeEmployeeClient
) : ExternalEmployeeApiPort {

    override suspend fun create(employee: Employee): ExternalEmployee {
        return acmeClient.create(employee.toAcmeEmployeeDTO()).awaitSingleOrNull().let { httpResponse ->
            when (httpResponse?.status?.code) {
                201 -> httpResponse.body()
                else -> throw GeneralException(
                    message = "failed to create acme employee",
                    errorCode = "ACME_CLIENT_ERROR",
                    parameters = mapOf(
                        "employee" to employee,
                        "statusCode" to httpResponse?.status?.code
                    )
                )
            }
        }.toModel()
    }
}
