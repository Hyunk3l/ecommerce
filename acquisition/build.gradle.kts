plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.acquisition.HelloKt")
}
