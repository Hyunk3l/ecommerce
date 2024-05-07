plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.24"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.order.HelloKt")
}
