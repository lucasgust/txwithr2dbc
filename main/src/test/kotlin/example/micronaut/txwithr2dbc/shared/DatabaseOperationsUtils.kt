package example.micronaut.txwithr2dbc.shared

import io.micronaut.data.connection.jdbc.operations.DefaultDataSourceConnectionOperations

fun DefaultDataSourceConnectionOperations.read(query: String) = executeRead {
    it.connection.createStatement().executeQuery(query)
        .use { generateSequence { if (it.next()) it.getString(1) else null }.toList() }
}

fun DefaultDataSourceConnectionOperations.write(queries: List<String>) {
    executeWrite {
        it.connection.createStatement().apply {
            queries.forEach(::addBatch)
            executeBatch()
        }
    }
}
