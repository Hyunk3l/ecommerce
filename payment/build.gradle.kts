plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.21"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.payment.HelloKt")
}
