plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.notification.HelloKt")
}
