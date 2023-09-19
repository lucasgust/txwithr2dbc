package example.micronaut.txwithr2dbc.shared

import io.micronaut.data.connection.jdbc.operations.DefaultDataSourceConnectionOperations
import jakarta.inject.Singleton

@Singleton
class DatabasePopulatorService(
    private val dataSource: DefaultDataSourceConnectionOperations
) {

    operator fun invoke(sqlFile: String) {
        val file = javaClass.getResource(sqlFile)
            ?.readText()
            ?: error("$sqlFile not found")

        dataSource.write(queries = QUERY_REGEX.findAll(file).map { it.value.trim() }.toList())
    }

    companion object {
        private val QUERY_REGEX = "(?s)(.*?;)".toRegex()
    }
}
