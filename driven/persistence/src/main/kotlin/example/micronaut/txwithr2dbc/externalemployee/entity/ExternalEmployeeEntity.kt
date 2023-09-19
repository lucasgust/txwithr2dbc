package example.micronaut.txwithr2dbc.externalemployee.entity

import example.micronaut.txwithr2dbc.employee.entity.toEntity
import example.micronaut.txwithr2dbc.shared.Auditable
import io.micronaut.data.annotation.EmbeddedId
import io.micronaut.data.annotation.MappedEntity
import example.micronaut.txwithr2dbc.externalemployee.model.ExternalEmployee as ExternalEmployeeModel

typealias ExternalEmployeeEntity = ExternalEmployee

@MappedEntity("external_employee")
data class ExternalEmployee(

    @EmbeddedId
    val id: ExternalEmployeeId

) : Auditable() {

    fun toModel() = ExternalEmployeeModel(
        employee = id.employee.toModel(),
        companyName = id.companyName
    )
}


fun ExternalEmployeeModel.toEntity() = ExternalEmployeeEntity(
    id = ExternalEmployeeId(
        employee = employee.toEntity(),
        companyName = companyName
    )
)