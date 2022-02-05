package com.fabridinapoli.shopping.domain.model

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import java.util.UUID

private const val MAX_PRODUCTS_ALLOWED_PER_CART = 15

data class ShoppingCart(val id: ShoppingCartId, val userId: UserId, val products: List<ProductId>) {
    fun addProduct(productId: ProductId): ShoppingCart = this.copy(products = this.products + productId)

    companion object {
        fun from(id: String, userId: String, products: List<String>): Either<DomainError, ShoppingCart> {
            if (products.count() > MAX_PRODUCTS_ALLOWED_PER_CART) {
                return DomainError("Too many products. Max allowed is $MAX_PRODUCTS_ALLOWED_PER_CART").left()
            }

            return ShoppingCart(
                id = ShoppingCartId.from(id),
                userId = UserId.from(userId),
                products = products.map { ProductId(it) }
            ).right()
        }
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
