package com.fabridinapoli.shopping.infrastructure.outbound.database

import arrow.core.left
import arrow.core.right
import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.UserId
import com.fabridinapoli.shopping.domain.model.buildShoppingCart
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate

@Tag("integration")
class PostgresShoppingCartRepositoryShould {

    private val databaseContainer = DatabaseContainer()

    private val jdbcTemplate = JdbcTemplate(databaseContainer.dataSource)

    private var objectMapper = ObjectMapper().registerKotlinModule()

    private val repository = PostgresShoppingCartRepository(jdbcTemplate, objectMapper)

    @Test
    fun `save a new shopping cart`() {
        val shoppingCart = buildShoppingCart()

        repository.save(shoppingCart)

        shoppingCart shouldBe repository.findOrNew(shoppingCart.id, shoppingCart.userId)
    }

    @Test
    fun `find an existing shopping cart`() {
        val shoppingCart = buildShoppingCart()
        repository.save(shoppingCart)

        val existingShoppingCart = repository.findOrNew(shoppingCart.id, shoppingCart.userId)

        shoppingCart shouldBe existingShoppingCart
    }

    @Test
    fun `create a new shopping cart if there isn't one with id and user id`() {
        val shoppingCartId = ShoppingCartId(UUID.randomUUID())
        val userId = UserId(UUID.randomUUID())
        val expectedShoppingCart = buildShoppingCart(id = shoppingCartId, userId = userId)

        val newShoppingCart = repository.findOrNew(shoppingCartId, userId)

        expectedShoppingCart shouldBe newShoppingCart
    }

    @Test
    fun `update the shopping cart if it already exists`() {
        val shoppingCart = buildShoppingCart()
        repository.save(shoppingCart = shoppingCart)

        val updatedShoppingCart = shoppingCart.addProduct(ProductId(UUID.randomUUID().toString()))
        updatedShoppingCart.map { repository.save(it) }

        updatedShoppingCart shouldBe repository.findOrNew(shoppingCart.id, shoppingCart.userId).right()
    }

    @Test
    fun `fail if the user has already one shopping cart`() {
        val shoppingCart = buildShoppingCart()
        repository.save(shoppingCart = shoppingCart)
        val anotherShoppingCart = buildShoppingCart(userId = shoppingCart.userId)

        val result = repository.save(shoppingCart = anotherShoppingCart)

        DomainError("User has already one shopping cart").left() shouldBe result
    }
}
