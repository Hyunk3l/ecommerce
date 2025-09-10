plugins {
    id("org.jetbrains.kotlin.jvm") version "2.2.20"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.order.HelloKt")
}
