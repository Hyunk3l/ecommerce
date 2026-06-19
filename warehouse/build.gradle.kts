import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.4.0"
    kotlin("plugin.spring") version "2.4.0"
}

group = "com.fabridinapoli.warehouse"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.22.0")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.3.1")
    implementation("org.apache.kafka:kafka-streams:4.3.0")
    implementation("io.arrow-kt:arrow-core:2.2.3")
    implementation("org.flywaydb:flyway-core:12.9.0")
    implementation("org.flywaydb:flyway-database-postgresql:12.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.4.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.11.0")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework:spring-tx:7.0.8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.springframework.boot:spring-boot-webflux-test")
    testImplementation("io.projectreactor:reactor-test:3.8.6")
    testImplementation("org.testcontainers:testcontainers:1.21.4")
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
    testImplementation("org.testcontainers:postgresql:1.21.4")
    testImplementation("io.kotest:kotest-assertions-core-jvm:6.2.1")
    testImplementation("io.kotest:kotest-assertions-json:6.2.1")
    testImplementation("io.kotest:kotest-runner-junit5:6.2.1")
    testImplementation("io.rest-assured:rest-assured:6.0.0")
    testImplementation("io.rest-assured:json-path:6.0.0")
    testImplementation("io.rest-assured:xml-path:6.0.0")
    testImplementation("io.rest-assured:json-schema-validator:6.0.0")
    testImplementation("io.mockk:mockk:1.14.11")
    testImplementation("com.ninja-squad:springmockk:5.0.1")
    testImplementation("net.java.dev.jna:jna:5.19.1")
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

extra["kotlin-coroutines.version"] = "1.6.0"
