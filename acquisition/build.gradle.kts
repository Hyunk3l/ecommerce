plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.acquisition.HelloKt")
}
