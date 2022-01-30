package com.fabridinapoli.shopping.domain.model

sealed class DomainEvent(val eventId: Any)

data class ProductAddedToShoppingCartEvent(
    val shoppingCartId: ShoppingCartId,
    val productId: ProductId
) : DomainEvent(eventId = shoppingCartId)
