plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    application
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("com.fabridinapoli.acquisition.HelloKt")
}
