plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.10"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.shipping.HelloKt")
}
