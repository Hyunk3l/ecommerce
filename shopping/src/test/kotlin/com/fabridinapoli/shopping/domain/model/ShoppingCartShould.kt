package com.fabridinapoli.shopping.domain.model

import arrow.core.left
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class ShoppingCartShould : StringSpec({
    "add a product to a new cart" {
        val productId = ProductId(UUID.randomUUID().toString())
        val shoppingCart = ShoppingCart(
            id = ShoppingCartId(UUID.randomUUID()),
            userId = UserId(UUID.randomUUID()),
            products = emptyList(),
        )
        val shoppingCartWithOneProduct = ShoppingCart.from(
            id = shoppingCart.id.id.toString(),
            userId = shoppingCart.userId.value.toString(),
            products = listOf(productId.value),
        )

        val updatedShoppingCart = shoppingCart.addProduct(productId)

        updatedShoppingCart shouldBe shoppingCartWithOneProduct
    }

    "fail if list of products is bigger than 15" {
        val products = (1..16).map { UUID.randomUUID().toString() }

        ShoppingCart.from(
            id = UUID.randomUUID().toString(),
            userId = UUID.randomUUID().toString(),
            products = products,
        ) shouldBe DomainError("Too many products. Max allowed is 15").left()
    }
})
