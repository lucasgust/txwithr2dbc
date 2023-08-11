import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val javaVersion: String by project
val micronautVersion: String by project
val reactorKotlinExtensionVersion: String by project
val logbackEncoderVersion: String by project
val logbackClassicVersion: String by project

plugins {
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    val kotlinVersion = "1.9.0"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
}

allprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("kotlin-allopen")
    }

    dependencies {
        kapt("io.micronaut.platform:micronaut-platform:$micronautVersion")
        kapt("io.micronaut:micronaut-core-bom:$micronautVersion")
        kapt("io.micronaut:micronaut-inject-java")

        implementation(platform("io.micronaut.platform:micronaut-platform:$micronautVersion"))
        implementation(platform("io.micronaut:micronaut-core-bom:$micronautVersion"))

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")
        implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
        implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$reactorKotlinExtensionVersion")
        implementation("io.micronaut:micronaut-aop")
        implementation("io.micronaut.reactor:micronaut-reactor")
        implementation("io.micronaut.data:micronaut-data-jdbc")

        implementation("io.micronaut:micronaut-inject")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        runtimeOnly("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion") {
            exclude("ch.qos.logback")
            implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
        }

        compileOnly("org.graalvm.nativeimage:svm")
        runtimeOnly("org.yaml:snakeyaml")
    }

    group = "example.micronaut.txwithr2dbc"
    version = "1.0.0"

    sourceSets {
        main { java.setSrcDirs(listOf("src/main/kotlin")) }
        test { java.setSrcDirs(listOf("src/test/kotlin")) }
    }

    kotlin {
        sourceSets {
            main { kotlin.setSrcDirs(listOf("src/main/kotlin")) }
            test { kotlin.setSrcDirs(listOf("src/test/kotlin")) }
        }
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }

        compileKotlin {
            kotlinOptions {
                jvmTarget = javaVersion
            }
        }

        compileTestKotlin {
            kotlinOptions {
                jvmTarget = javaVersion
            }
        }

        test {
            useJUnitPlatform()

            testLogging {
                showStandardStreams = true
                events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.STARTED, TestLogEvent.SKIPPED)
                exceptionFormat = TestExceptionFormat.FULL
            }
        }
    }
}

tasks {
    build {
        setDependsOn(subprojects.map { it.getTasksByName("build", false) })
    }

    check {
        setDependsOn(subprojects.map { it.getTasksByName("check", false) })
    }
}