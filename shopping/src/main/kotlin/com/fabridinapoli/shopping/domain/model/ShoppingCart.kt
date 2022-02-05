package com.fabridinapoli.shopping.domain.model

import java.util.UUID

data class ShoppingCart(val id: ShoppingCartId, val userId: UserId, val products: List<ProductId>) {
    fun addProduct(productId: ProductId): ShoppingCart = this.copy(products = this.products + productId)

    companion object {
        fun from(id: String, userId: String, products: List<ProductId>) = ShoppingCart(
            id = ShoppingCartId.from(id),
            userId = UserId.from(userId),
            products = products
        )
    }
}

data class ShoppingCartId(val id: UUID) {
    companion object {
        fun from(value: String) = ShoppingCartId(id = UUID.fromString(value))
    }
}

data class User(val userId: UserId)

data class UserId(val value: UUID) {
    companion object {
        fun from(value: String) = UserId(UUID.fromString(value))
    }
}
