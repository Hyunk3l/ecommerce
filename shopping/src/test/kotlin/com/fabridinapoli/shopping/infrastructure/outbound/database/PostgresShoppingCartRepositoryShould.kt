package com.fabridinapoli.shopping.infrastructure.outbound.database

import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.buildShoppingCart
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate

@Tag("integration")
class PostgresShoppingCartRepositoryShould {

    private val databaseContainer = DatabaseContainer()

    private val jdbcTemplate = JdbcTemplate(databaseContainer.dataSource)

    private var objectMapper = ObjectMapper().registerKotlinModule()

    @Test
    fun `save a new shopping cart`() {
        val shoppingCart = buildShoppingCart()
        val repository = PostgresShoppingCartRepository(jdbcTemplate, objectMapper)

        repository.save(shoppingCart)

        shoppingCart shouldBe repository.findOrNew(shoppingCart.id, shoppingCart.userId)
    }
}
