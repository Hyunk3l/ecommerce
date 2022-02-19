import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.fabridinapoli"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator:2.6.3")
	implementation("org.springframework.boot:spring-boot-starter-jdbc:2.6.3")
	implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.3")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5")
	implementation("org.apache.kafka:kafka-streams:3.1.0")
	implementation("io.arrow-kt:arrow-core:1.0.1")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.0")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("io.projectreactor:reactor-test:3.4.14")
	testImplementation("org.testcontainers:testcontainers:1.16.3")
	testImplementation("org.testcontainers:junit-jupiter:1.16.3")
	testImplementation("org.testcontainers:postgresql:1.16.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:5.1.0")
	testImplementation("io.kotest:kotest-assertions-json:5.1.0")
	testImplementation("io.kotest:kotest-runner-junit5:5.1.0")
	testImplementation("io.rest-assured:rest-assured:4.5.0")
	testImplementation("io.rest-assured:json-path:4.5.0")
	testImplementation("io.rest-assured:xml-path:4.5.0")
	testImplementation("io.rest-assured:json-schema-validator:4.5.0")
	testImplementation("io.mockk:mockk:1.12.2")
	testImplementation("com.ninja-squad:springmockk:3.1.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

extra["kotlin-coroutines.version"] = "1.6.0"
