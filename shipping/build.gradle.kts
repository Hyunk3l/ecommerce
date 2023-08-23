plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.shipping.HelloKt")
}
