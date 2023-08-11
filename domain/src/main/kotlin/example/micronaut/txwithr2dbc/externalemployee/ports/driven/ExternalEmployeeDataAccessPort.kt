package example.micronaut.txwithr2dbc.externalemployee.ports.driven

import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee

interface ExternalEmployeeDataAccessPort {

    suspend fun save(externalEmployee: ExternalEmployee): ExternalEmployee

    suspend fun count(): Long
}
