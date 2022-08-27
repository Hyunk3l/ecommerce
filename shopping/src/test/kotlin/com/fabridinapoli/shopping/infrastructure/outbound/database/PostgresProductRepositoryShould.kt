package com.fabridinapoli.shopping.infrastructure.outbound.database

import arrow.core.right
import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.Title
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate

@Tag("integration")
class PostgresProductRepositoryShould {

    private val databaseContainer = DatabaseContainer()

    private val jdbcTemplate = JdbcTemplate(databaseContainer.dataSource)

    private val repository = PostgresProductRepository(jdbcTemplate)

    @Test
    fun `save a product correctly`() {
        val product = Product(
            id = ProductId.from(UUID.randomUUID()),
            title = Title.from("random title"),
            price = Price.from(10.0)
        )

        repository.save(listOf(product))

        listOf(product).right() shouldBe repository.find()
    }
}
