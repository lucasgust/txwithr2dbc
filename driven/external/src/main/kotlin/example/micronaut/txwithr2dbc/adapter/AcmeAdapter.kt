package example.micronaut.txwithr2dbc.adapter

import example.micronaut.txwithr2dbc.client.AcmeEmployeeClient
import example.micronaut.txwithr2dbc.dto.toAcmeEmployeeDTO
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee
import example.micronaut.txwithr2dbc.externalemployee.ports.driven.ExternalEmployeeApiPort
import jakarta.inject.Singleton
import kotlinx.coroutines.reactor.awaitSingle
import java.util.concurrent.atomic.AtomicLong

@Singleton
class AcmeAdapter(
    private val acmeClient: AcmeEmployeeClient
) : ExternalEmployeeApiPort {

    private val counter = AtomicLong()

    override suspend fun create(employee: Employee): ExternalEmployee {
        return acmeClient.create(employee.toAcmeEmployeeDTO()).awaitSingle().let { httpResponse ->
            // simulating error every 5...
            if (counter.incrementAndGet() % 5L == 0L) error("whoops... forced error")

            when (httpResponse.status.code) {
                201 -> httpResponse.body()
                else -> error("failed to create acme employee=$employee")
            }
        }.toModel()
    }
}
