package com.fabridinapoli.shopping.infrastructure.outbound.database

import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
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
        val event = ProductAddedToShoppingCartEvent(
            ShoppingCartId(UUID.randomUUID()),
            ProductId(UUID.randomUUID().toString())
        )
        val outboxRepository = mockk<OutboxRepository>()
        every { outboxRepository.save(event) } just Runs

        DefaultOutboxDomainEventPublisher(outboxRepository).publish(event)

        verify { outboxRepository.save(event) }
    }
}
