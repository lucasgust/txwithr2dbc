dependencies {
    implementation(project(":domain"))

    kapt("io.micronaut.data:micronaut-data-processor")

    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    implementation("io.micronaut.flyway:micronaut-flyway")

    runtimeOnly("io.r2dbc:r2dbc-pool")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("mysql:mysql-connector-java")
    runtimeOnly("dev.miku:r2dbc-mysql")
    runtimeOnly("org.flywaydb:flyway-mysql")
}
