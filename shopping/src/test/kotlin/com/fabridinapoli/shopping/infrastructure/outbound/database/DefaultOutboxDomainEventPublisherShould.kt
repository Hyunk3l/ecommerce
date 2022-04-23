package com.fabridinapoli.shopping.infrastructure.outbound.database

import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.EventId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxEvent
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID
import org.junit.jupiter.api.Test

class DefaultOutboxDomainEventPublisherShould {
    @Test
    fun `publish an event in the outbox`() {
        val shoppingCartId = ShoppingCartId(UUID.randomUUID())
        val event = ProductAddedToShoppingCartEvent(
            shoppingCartId,
            ProductId(UUID.randomUUID().toString())
        )
        val outboxEvent = OutboxEvent(
            id = EventId(shoppingCartId.id.toString()),
            type = event.javaClass.kotlin.qualifiedName.toString(),
            event = event
        )
        val outboxRepository = mockk<OutboxRepository>()
        every { outboxRepository.save(outboxEvent) } just Runs

        DefaultOutboxDomainEventPublisher(outboxRepository).publish(event)

        verify { outboxRepository.save(outboxEvent) }
    }
}
