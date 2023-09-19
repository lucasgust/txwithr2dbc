package example.micronaut.txwithr2dbc.shared

import io.micronaut.data.connection.jdbc.operations.DefaultDataSourceConnectionOperations
import jakarta.inject.Singleton

@Singleton
class DatabaseCleanUpService(
    private val dataSource: DefaultDataSourceConnectionOperations
) {

    operator fun invoke() {
        dataSource.write(
            queries = mutableListOf<String>().apply {
                add(DISABLE_FK_QUERY)
                addAll(getTablesNames().map { DELETE_TABLE_QUERY.format(it) })
                add(ENABLE_FK_QUERY)
            }
        )
    }

    private fun getTablesNames() = TABLE_NAMES.ifEmpty {
        dataSource.read(TABLES_NAMES_QUERY).also { TABLE_NAMES.addAll(it) }
    }

    companion object {
        private const val DISABLE_FK_QUERY = "SET FOREIGN_KEY_CHECKS = 0;"
        private const val ENABLE_FK_QUERY = "SET FOREIGN_KEY_CHECKS = 1;"
        private const val DELETE_TABLE_QUERY = "DELETE FROM %s;"

        private const val TABLES_NAMES_QUERY = """
            SELECT
                TABLE_NAME 
            FROM
                INFORMATION_SCHEMA.TABLES 
            WHERE
                    TABLE_SCHEMA = SCHEMA()
                AND UPPER(TABLE_NAME) <> 'FLYWAY_SCHEMA_HISTORY';
        """

        private val TABLE_NAMES = mutableListOf<String>()
    }
}
