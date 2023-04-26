plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.21"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.notification.HelloKt")
}
