package com.fabridinapoli.shopping.domain.model

import java.util.UUID

fun buildShoppingCart(
    id: ShoppingCartId = ShoppingCartId(UUID.randomUUID()),
    userId: UserId = UserId(UUID.randomUUID()),
    products: List<ProductId> = emptyList(),
): ShoppingCart = ShoppingCart(
    id = id,
    userId = userId,
    products = products,
)
