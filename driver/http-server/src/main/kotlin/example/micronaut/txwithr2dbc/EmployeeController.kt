package example.micronaut.txwithr2dbc

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driver.CreateEmployeePort
import example.micronaut.txwithr2dbc.employee.ports.driver.GetEmployeePort
import example.micronaut.txwithr2dbc.employee.ports.driver.ListEmployeePort
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import org.instancio.Instancio
import java.util.UUID

@Controller("/employees")
class EmployeeController(
    private val getEmployeePort: GetEmployeePort,
    private val listEmployeePort: ListEmployeePort,
    private val createEmployeePort: CreateEmployeePort
) {

    @Get(value = "/{id}", produces = [APPLICATION_JSON])
    suspend fun get(@PathVariable id: UUID): Employee {
        return getEmployeePort.getById(id)
    }

    @Get(produces = [APPLICATION_JSON])
    suspend fun list(): List<Employee> {
        return listEmployeePort.list()
    }

    @Post(produces = [APPLICATION_JSON])
    suspend fun create(@Body employee: Employee? = null): Employee {
        return createEmployeePort.create(employee = employee ?: Instancio.create(Employee::class.java))
    }
}
