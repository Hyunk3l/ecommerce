plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.notification.HelloKt")
}
