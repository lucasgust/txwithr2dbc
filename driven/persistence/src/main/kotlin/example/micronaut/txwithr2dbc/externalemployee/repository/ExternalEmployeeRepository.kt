package example.micronaut.txwithr2dbc.externalemployee.repository

import example.micronaut.txwithr2dbc.externalemployee.entity.ExternalEmployee
import example.micronaut.txwithr2dbc.externalemployee.entity.ExternalEmployeeId
import example.micronaut.txwithr2dbc.shared.Repository.ReadRepository
import example.micronaut.txwithr2dbc.shared.Repository.WriteRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.transaction.TransactionDefinition.Propagation.MANDATORY
import io.micronaut.transaction.annotation.Transactional

@R2dbcRepository(dialect = MYSQL)
interface ExternalEmployeeReadRepository : ReadRepository<ExternalEmployee, ExternalEmployeeId>

@Transactional(propagation = MANDATORY)
@R2dbcRepository(dialect = MYSQL)
interface ExternalEmployeeWriteRepository : WriteRepository<ExternalEmployee, ExternalEmployeeId>
