package com.fabridinapoli.shipping

class Hello {
    val greeting: String
        get() {
            return "Hello World from ${this::class.java}!"
        }
}

fun main() {
    println(Hello().greeting)
}
