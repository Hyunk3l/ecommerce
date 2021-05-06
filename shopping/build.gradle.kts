import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.0-RC"
	kotlin("plugin.spring") version "1.5.0-RC"
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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.apache.kafka:kafka-streams")
	implementation("io.arrow-kt:arrow-core:0.13.2")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.testcontainers:testcontainers:1.15.3")
	testImplementation("org.testcontainers:junit-jupiter:1.15.3")
	testImplementation("org.testcontainers:postgresql:1.15.3")
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
	testImplementation("io.kotest:kotest-assertions-json:4.4.3")
	testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
	testImplementation("io.rest-assured:rest-assured:4.3.3")
	testImplementation("io.mockk:mockk:1.11.0")
	testImplementation("com.ninja-squad:springmockk:3.0.1")
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
