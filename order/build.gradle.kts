plugins {
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.order.HelloKt")
}
