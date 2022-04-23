package com.fabridinapoli.shopping.domain.model

import java.util.UUID

sealed class DomainEvent(val eventId: UUID)

data class ProductAddedToShoppingCartEvent(
    val shoppingCartId: ShoppingCartId,
    val productId: ProductId
) : DomainEvent(eventId = shoppingCartId.id)
