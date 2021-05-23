package com.fabridinapoli.shopping.infrastructure.outbound.outbox

data class OutboxShoppingCartEvent(val id: String, val type: String)
