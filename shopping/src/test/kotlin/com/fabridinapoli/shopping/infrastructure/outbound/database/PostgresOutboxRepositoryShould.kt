package com.fabridinapoli.shopping.infrastructure.outbound.database

import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.EventId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxEvent
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate

@Tag("integration")
class PostgresOutboxRepositoryShould {

    private val databaseContainer = DatabaseContainer()

    private val jdbcTemplate = JdbcTemplate(databaseContainer.dataSource)

    private var objectMapper = ObjectMapper().registerKotlinModule()

    private val repository = PostgresOutboxRepository(jdbcTemplate, objectMapper)

    @Test
    fun `save an event in the outbox`() {
        val type = ProductAddedToShoppingCartEvent::class.qualifiedName.toString()
        val eventId = EventId(UUID.randomUUID().toString())
        val event = ProductAddedToShoppingCartEvent(
            ShoppingCartId.from(eventId.id),
            ProductId(UUID.randomUUID().toString())
        )
        val outboxEvent = OutboxEvent(eventId, type, event)

        repository.save(outboxEvent)

        outboxEvent shouldBe repository.findLatestBy(eventId)
    }
}
