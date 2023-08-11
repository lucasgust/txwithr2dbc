package example.micronaut.txwithr2dbc

import example.micronaut.txwithr2dbc.employee.model.Employee
import example.micronaut.txwithr2dbc.employee.ports.driver.CreateEmployeePort
import example.micronaut.txwithr2dbc.employee.ports.driver.ListEmployeePort
import io.micronaut.http.MediaType.APPLICATION_JSON
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.instancio.Instancio

@Controller("/employee")
class EmployeeController(
    private val listEmployeePort: ListEmployeePort,
    private val createEmployeePort: CreateEmployeePort
) {

    @Get(produces = [APPLICATION_JSON])
    suspend fun list(): List<Employee> {
        return listEmployeePort.list()
    }

    @Post(produces = [APPLICATION_JSON])
    suspend fun create(): Employee {
        return createEmployeePort.create(employee = Instancio.create(Employee::class.java))
    }
}
