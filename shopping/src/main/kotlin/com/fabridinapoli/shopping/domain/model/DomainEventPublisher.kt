package com.fabridinapoli.shopping.domain.model

interface DomainEventPublisher {
    fun publish(domainEvent: DomainEvent)
}
