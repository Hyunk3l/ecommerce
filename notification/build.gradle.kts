plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.21"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.notification.HelloKt")
}
