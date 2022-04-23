package com.fabridinapoli.shopping.infrastructure.outbound.database

import com.fabridinapoli.shopping.domain.model.DomainEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCart
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.UserId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.EventId
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxRepository
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxEvent
import com.fasterxml.jackson.databind.ObjectMapper
import java.sql.ResultSet
import java.util.UUID
import org.postgresql.util.PGobject
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class PostgresOutboxRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val objectMapper: ObjectMapper
) : OutboxRepository {
    override fun findLatestBy(eventId: EventId): OutboxEvent? {
        return jdbcTemplate.queryForObject("""
            SELECT * FROM outbox WHERE id = ? LIMIT 1
        """.trimIndent(), UUID.fromString(eventId.id)){ rs: ResultSet, _: Any -> rs.resultSetToEvent() }
    }

    override fun save(outboxEvent: OutboxEvent) {
        jdbcTemplate.update("""
            INSERT INTO outbox (id, type, event) VALUES(?, ?, ?)
        """.trimIndent(), UUID.fromString(outboxEvent.id.id), outboxEvent.type, outboxEvent.event.toDatabaseObject())
    }
    private fun DomainEvent.toDatabaseObject(): PGobject {
        val jsonObject = PGobject()
        jsonObject.type = "json"
        jsonObject.value = objectMapper.writeValueAsString(this)
        return jsonObject
    }
    private fun ResultSet.resultSetToEvent() = objectMapper
        .readValue(this.getObject("event").toString(), Class.forName(this.getString("type")))
        .let {
            OutboxEvent(
                id = EventId(this.getString("id")),
                type = this.getString("type"),
                event = it as DomainEvent
            )
        }
}
