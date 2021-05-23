package com.fabridinapoli.shopping.infrastructure.outbound.outbox

import com.fabridinapoli.shopping.domain.model.ShoppingCartId

class InMemoryOutboxRepository : OutboxRepository {

    private var events = emptyList<OutboxShoppingCartEvent>()

    override fun findLatestBy(shoppingCartId: ShoppingCartId): OutboxShoppingCartEvent? =
        events.findLast { it.id == shoppingCartId.id.toString() }
}
