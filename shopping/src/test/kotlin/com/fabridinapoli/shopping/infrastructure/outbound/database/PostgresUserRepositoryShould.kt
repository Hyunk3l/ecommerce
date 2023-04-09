package com.fabridinapoli.shopping.infrastructure.outbound.database

import arrow.core.right
import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.Title
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.support.TransactionTemplate

class PostgresUserRepositoryShould {

    private val databaseContainer = DatabaseContainer()

    private val jdbcTemplate = JdbcTemplate(databaseContainer.dataSource)

    private val transactionManager = JdbcTransactionManager(databaseContainer.dataSource)

    private val repository = PostgresUserRepository(jdbcTemplate)

    private val productRepository = PostgresProductRepository(jdbcTemplate)

    @Test
    fun `save a user correctly`() {
        val transactionTemplate = TransactionTemplate(transactionManager)
        val product = Product(
            id = ProductId.from(UUID.randomUUID()),
            title = Title.from("random title"),
            price = Price.from(10.0)
        )

        val transactionStatus = transactionManager.getTransaction(transactionTemplate)
        transactionTemplate.execute {
            productRepository.save(listOf(product))
            repository
                .saveEither()
                .mapLeft { transactionManager.rollback(transactionStatus) }
        }

        transactionStatus.isCompleted shouldBe true
        productRepository.find() shouldBe emptyList<Product>().right()
    }
}
