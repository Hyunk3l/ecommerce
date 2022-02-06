package com.fabridinapoli.shopping.domain.model

import arrow.core.flatMap
import arrow.core.left
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class ShoppingCartShould : StringSpec({

    val productId = ProductId(UUID.randomUUID().toString())
    val shoppingCartId = ShoppingCartId(UUID.randomUUID())
    val userId = UserId(UUID.randomUUID())

    "add a product to a new cart" {
        val shoppingCart = ShoppingCart.from(
            id = shoppingCartId.id.toString(),
            userId = userId.value.toString(),
            products = emptyList(),
        )

        val updatedShoppingCart = shoppingCart.flatMap { it.addProduct(productId) }

        updatedShoppingCart shouldBe ShoppingCart.from(
            id = shoppingCartId.id.toString(),
            userId = userId.value.toString(),
            products = listOf(productId.value),
        )
    }

    "fail if list of products is bigger than 15" {
        val products = (1..16).map { UUID.randomUUID().toString() }

        ShoppingCart.from(
            id = shoppingCartId.id.toString(),
            userId = userId.value.toString(),
            products = products,
        ) shouldBe DomainError("Too many products. Max allowed is 15").left()
    }
})
