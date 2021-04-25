rootProject.name = "ecommerce"

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }
}

include("acquisition")
include("inventory")
include("notification")
include("payment")
include("order")
include("shopping")
include("shipping")
