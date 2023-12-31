import io.micronaut.gradle.MicronautRuntime.NETTY
import io.micronaut.gradle.MicronautTestRuntime.KOTEST_5

val kotestRunnerVersion: String by project
val mockkVersion: String by project
val instancioVersion: String by project
val assertjVersion: String by project

plugins {
    // https://plugins.gradle.org/plugin/io.micronaut.application
    val micronautVersion = "4.1.1"
    // https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
    val shadowVersion = "8.1.1"

    application
    id("io.micronaut.application") version micronautVersion
    id("io.micronaut.test-resources") version micronautVersion
    id("com.github.johnrengelman.shadow") version shadowVersion
}

kapt {
    arguments {
        arg("micronaut.processing.incremental", true)
        arg("micronaut.processing.annotations", "example.micronaut.txwithr2dbc.*")
    }
}

application {
    mainClass.set("example.micronaut.txwithr2dbc.ApplicationKt")

    applicationDefaultJvmArgs = listOf(
        "-server",
        "-XX:+UseNUMA",
        "-XX:+UseParallelGC"
    )
}

graalvmNative {
    toolchainDetection.set(true)
}

micronaut {
    runtime(NETTY)
    testRuntime(KOTEST_5)

    processing {
        incremental(true)
        annotations("example.micronaut.txwithr2dbc.*")
    }

    testResources {
        additionalModules.addAll("r2dbc-mysql", "jdbc-mysql")
        enabled.set(true)
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("app")
        archiveVersion.set("")
        archiveAppendix.set("")
        archiveClassifier.set("")
    }
}

dependencies {
    listOf(
        ":domain",
        // DRIVEN
        ":persistence",
        ":external",
        // DRIVER
        ":http-server"
    ).forEach {
        implementation(project(it))
        testImplementation(project(it))
    }

    kaptTest("io.micronaut.data:micronaut-data-processor")

    testImplementation("io.micronaut.data:micronaut-data-jdbc")
    testImplementation("io.micronaut.data:micronaut-data-r2dbc")
    testImplementation("io.micronaut.test:micronaut-test-rest-assured")
    testImplementation("com.github.tomakehurst:wiremock:3.0.0-beta-10")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestRunnerVersion")
    testImplementation("io.micronaut.test:micronaut-test-kotest5")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.instancio:instancio-junit:$instancioVersion")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")

    testResourcesService("mysql:mysql-connector-java")
}
