package com.fabridinapoli.shopping.infrastructure.outbound.database

import com.fabridinapoli.shopping.domain.model.DomainEvent
import com.fabridinapoli.shopping.domain.model.DomainEventPublisher
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.EventId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxEvent
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxRepository

/**
 * Simplified (and wrong) implementation of the domain event publisher, using directly the outbox repo.
 */
class DefaultOutboxDomainEventPublisher(private val outboxRepository: OutboxRepository) : DomainEventPublisher {
    override fun publish(domainEvent: DomainEvent) {
        outboxRepository.save(OutboxEvent(
            id = EventId(domainEvent.eventId.toString()),
            type = domainEvent.javaClass.kotlin.qualifiedName.toString(),
            event = domainEvent
        ))
    }
}
