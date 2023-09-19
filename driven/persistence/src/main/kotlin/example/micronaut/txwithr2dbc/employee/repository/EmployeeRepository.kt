package example.micronaut.txwithr2dbc.employee.repository

import example.micronaut.txwithr2dbc.employee.entity.EmployeeEntity
import example.micronaut.txwithr2dbc.shared.Repository.ReadRepository
import example.micronaut.txwithr2dbc.shared.Repository.WriteRepository
import io.micronaut.data.model.query.builder.sql.Dialect.MYSQL
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.transaction.TransactionDefinition.Propagation.MANDATORY
import io.micronaut.transaction.annotation.Transactional
import java.util.UUID

@R2dbcRepository(dialect = MYSQL)
interface EmployeeReadRepository : ReadRepository<EmployeeEntity, UUID>

@Transactional(propagation = MANDATORY)
@R2dbcRepository(dialect = MYSQL)
interface EmployeeWriteRepository : WriteRepository<EmployeeEntity, UUID>