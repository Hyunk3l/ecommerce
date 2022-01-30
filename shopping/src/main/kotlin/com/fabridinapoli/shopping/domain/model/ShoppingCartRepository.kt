package com.fabridinapoli.shopping.domain.model

import arrow.core.Either

interface ShoppingCartRepository {
    fun save(shoppingCart: ShoppingCart): Either<DomainError, Unit>
    fun findOrNew(shoppingCartId: ShoppingCartId, userId: UserId): ShoppingCart
}
