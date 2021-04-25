plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.order.HelloKt")
}