val instancioVersion: String by project

dependencies {
    implementation(project(":domain"))

    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("org.instancio:instancio-junit:$instancioVersion")
}
