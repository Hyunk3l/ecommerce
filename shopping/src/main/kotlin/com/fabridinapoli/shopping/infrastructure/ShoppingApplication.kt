package com.fabridinapoli.shopping.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.fabridinapoli.shopping.infrastructure"])
@SpringBootApplication
class ShoppingApplication

fun main(args: Array<String>) {
	runApplication<ShoppingApplication>(*args)
}
