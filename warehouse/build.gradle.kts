import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.spring") version "2.1.21"
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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.20.0")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.4")
    implementation("org.apache.kafka:kafka-streams:4.1.0")
    implementation("io.arrow-kt:arrow-core:2.1.2")
    implementation("org.flywaydb:flyway-core:11.14.1")
    implementation("org.flywaydb:flyway-database-postgresql:11.14.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.2.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.2.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework:spring-tx:6.2.12")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("io.projectreactor:reactor-test:3.7.12")
    testImplementation("org.testcontainers:testcontainers:1.21.3")
    testImplementation("org.testcontainers:junit-jupiter:1.21.3")
    testImplementation("org.testcontainers:postgresql:1.21.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:6.0.4")
    testImplementation("io.kotest:kotest-assertions-json:6.0.4")
    testImplementation("io.kotest:kotest-runner-junit5:6.0.4")
    testImplementation("io.rest-assured:rest-assured:5.5.6")
    testImplementation("io.rest-assured:json-path:5.5.6")
    testImplementation("io.rest-assured:xml-path:5.5.6")
    testImplementation("io.rest-assured:json-schema-validator:5.5.6")
    testImplementation("io.mockk:mockk:1.14.6")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("net.java.dev.jna:jna:5.18.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

extra["kotlin-coroutines.version"] = "1.6.0"
