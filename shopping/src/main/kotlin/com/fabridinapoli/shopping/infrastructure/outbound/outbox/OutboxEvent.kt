package com.fabridinapoli.shopping.infrastructure.outbound.outbox

import com.fabridinapoli.shopping.domain.model.DomainEvent

data class OutboxEvent(val id: EventId, val type: String, val event: DomainEvent)
data class EventId(val id: String)
