package example.micronaut.txwithr2dbc.employee.entity

import example.micronaut.txwithr2dbc.shared.Auditable
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import java.util.UUID
import example.micronaut.txwithr2dbc.employee.model.Employee as EmployeeModel

typealias EmployeeEntity = Employee

@MappedEntity("employee")
data class Employee(

    @field:Id
    val id: UUID,

    val name: String

) : Auditable() {

    fun toModel() = EmployeeModel(
        id = id,
        name = name
    )
}

fun EmployeeModel.toEntity() = EmployeeEntity(
    id = id,
    name = name,
)