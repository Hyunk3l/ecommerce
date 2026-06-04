plugins {
    id("org.jetbrains.kotlin.jvm") version "2.4.0"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.shipping.HelloKt")
}
