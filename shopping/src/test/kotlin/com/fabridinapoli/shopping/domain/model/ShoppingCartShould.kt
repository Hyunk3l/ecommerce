package com.fabridinapoli.shopping.domain.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class ShoppingCartShould : StringSpec({
    "add a product to a new cart" {
        val productId = ProductId(UUID.randomUUID().toString())
        val shoppingCart = ShoppingCart(
            id = ShoppingCartId.from(UUID.randomUUID().toString()),
            userId = UserId(value = UUID.randomUUID()),
            products = emptyList(),
        )
        val shoppingCartWithOneProduct = ShoppingCart.from(
            id = shoppingCart.id.id.toString(),
            userId = shoppingCart.userId.value.toString(),
            products = listOf(productId),
        )

        val updatedShoppingCart = shoppingCart.addProduct(productId)

        updatedShoppingCart shouldBe shoppingCartWithOneProduct
    }
})
