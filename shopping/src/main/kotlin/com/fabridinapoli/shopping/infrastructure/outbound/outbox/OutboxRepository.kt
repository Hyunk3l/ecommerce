package com.fabridinapoli.shopping.infrastructure.outbound.outbox

import com.fabridinapoli.shopping.domain.model.ShoppingCartId

interface OutboxRepository {
    fun findLatestBy(shoppingCartId: ShoppingCartId): OutboxShoppingCartEvent?
}
