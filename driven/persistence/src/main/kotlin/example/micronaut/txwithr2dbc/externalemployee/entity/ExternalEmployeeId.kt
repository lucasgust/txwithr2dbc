package example.micronaut.txwithr2dbc.externalemployee.entity

import example.micronaut.txwithr2dbc.employee.entity.EmployeeEntity
import io.micronaut.data.annotation.Embeddable
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.data.annotation.Relation
import io.micronaut.data.annotation.Relation.Kind.MANY_TO_ONE

@Embeddable
data class ExternalEmployeeId(

    @Relation(value = MANY_TO_ONE)
    @MappedProperty(value = "employee_id")
    val employee: EmployeeEntity,

    @MappedProperty(value = "company_name")
    val companyName: String
)