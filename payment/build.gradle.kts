plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.payment.HelloKt")
}
