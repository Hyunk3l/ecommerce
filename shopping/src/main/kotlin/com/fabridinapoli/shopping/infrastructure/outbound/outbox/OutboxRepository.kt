package com.fabridinapoli.shopping.infrastructure.outbound.outbox

import com.fabridinapoli.shopping.domain.model.DomainEvent

interface OutboxRepository {
    fun findLatestBy(eventId: EventId): OutboxEvent?

    fun save(outboxEvent: OutboxEvent)
}
