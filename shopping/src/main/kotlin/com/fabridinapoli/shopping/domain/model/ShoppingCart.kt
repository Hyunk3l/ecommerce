package com.fabridinapoli.shopping.domain.model

import java.util.UUID

data class ShoppingCart(val id: ShoppingCartId, val userId: UserId, val products: List<ProductId>) {
    fun addProduct(productId: ProductId): ShoppingCart = this.copy(products = this.products + productId)
}

data class ShoppingCartId(val id: UUID) {
    companion object {
        fun from(value: String) = ShoppingCartId(id = UUID.fromString(value))
    }
}
