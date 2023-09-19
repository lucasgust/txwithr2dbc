package example.micronaut.txwithr2dbc.employee.adapter

import example.micronaut.txwithr2dbc.employee.entity.toEntity
import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driven.EmployeeDataAccessPort
import example.micronaut.txwithr2dbc.employee.repository.EmployeeReadRepository
import example.micronaut.txwithr2dbc.employee.repository.EmployeeWriteRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import org.slf4j.LoggerFactory
import java.util.UUID

@Singleton
open class EmployeeDataAccessAdapter(
    private val employeeReadRepository: EmployeeReadRepository,
    private val employeeWriteRepository: EmployeeWriteRepository
) : EmployeeDataAccessPort {

    override suspend fun save(employee: Employee): Employee {
        return employeeWriteRepository.save(employee.toEntity()).toModel()
    }

    override suspend fun findById(id: UUID): Employee? {
        logger.info("before") // context is fine
        val result = employeeReadRepository.findById(id)
        logger.info("after") // context is lost
        return result?.toModel()
    }

    override suspend fun findAll(): List<Employee> {
        logger.info("before") // context is fine
        val result = employeeReadRepository.findAll()
        logger.info("after/flow") // context is fine
        val resultList = result.toList().map { it.toModel() }
        logger.info("after/list")// context is lost
        return resultList
    }

    companion object {
        private val logger = LoggerFactory.getLogger(EmployeeDataAccessAdapter::class.java)
    }
}
