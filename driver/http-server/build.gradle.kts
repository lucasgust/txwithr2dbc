val instancioVersion: String by project

dependencies {
    implementation(project(":domain"))

    implementation("io.micronaut:micronaut-http")
    implementation("org.instancio:instancio-junit:$instancioVersion")
}
