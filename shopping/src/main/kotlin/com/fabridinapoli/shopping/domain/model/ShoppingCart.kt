package com.fabridinapoli.shopping.domain.model

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import java.util.UUID

private const val MAX_PRODUCTS_ALLOWED_PER_CART = 15

data class ShoppingCart internal constructor(
    val id: ShoppingCartId,
    val userId: UserId,
    val products: List<ProductId>
) {
    fun addProduct(productId: ProductId): Either<DomainError, ShoppingCart> {
        return (this.products + productId)
            .validate()
            .map { this.copy(products = it) }
    }

    companion object {
        fun from(id: String, userId: String, products: List<String>): Either<DomainError, ShoppingCart> {
            return products
                .toProducts()
                .flatMap {
                    ShoppingCart(
                        id = ShoppingCartId.from(id),
                        userId = UserId.from(userId),
                        products = products.map { ProductId(it) }
                    ).right()
                }
                .mapLeft { it }
        }

        private fun List<String>.toProducts(): Either<DomainError, List<ProductId>> {
            return this.map { ProductId(it) }.validate()
        }

        private fun List<ProductId>.validate(): Either<DomainError, List<ProductId>> {
            return if (this.count() > MAX_PRODUCTS_ALLOWED_PER_CART) {
                return DomainError("Too many products. Max allowed is $MAX_PRODUCTS_ALLOWED_PER_CART").left()
            } else this.right()
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
