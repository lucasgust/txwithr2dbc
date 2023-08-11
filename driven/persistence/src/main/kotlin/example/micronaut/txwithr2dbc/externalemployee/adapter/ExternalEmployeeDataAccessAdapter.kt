package example.micronaut.txwithr2dbc.externalemployee.adapter

import example.micronaut.txwithr2dbc.externalemployee.entity.toEntity
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee
import example.micronaut.txwithr2dbc.externalemployee.ports.driven.ExternalEmployeeDataAccessPort
import example.micronaut.txwithr2dbc.externalemployee.repository.ExternalEmployeeReadRepository
import example.micronaut.txwithr2dbc.externalemployee.repository.ExternalEmployeeWriteRepository
import jakarta.inject.Singleton

@Singleton
class ExternalEmployeeDataAccessAdapter(
    private val externalEmployeeReadRepository: ExternalEmployeeReadRepository,
    private val externalEmployeeWriteRepository: ExternalEmployeeWriteRepository
) : ExternalEmployeeDataAccessPort {

    override suspend fun save(externalEmployee: ExternalEmployee): ExternalEmployee {
        return externalEmployeeWriteRepository.save(externalEmployee.toEntity()).toModel()
    }

    override suspend fun count(): Long {
        return externalEmployeeReadRepository.count()
    }
}
