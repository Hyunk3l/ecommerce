plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.10"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.payment.HelloKt")
}
