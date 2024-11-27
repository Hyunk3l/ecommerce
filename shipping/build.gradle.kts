plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.shipping.HelloKt")
}
