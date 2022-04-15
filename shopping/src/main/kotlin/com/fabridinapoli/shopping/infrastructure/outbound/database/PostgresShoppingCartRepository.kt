package com.fabridinapoli.shopping.infrastructure.outbound.database

import arrow.core.Either
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCart
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.ShoppingCartRepository
import com.fabridinapoli.shopping.domain.model.UserId
import com.fasterxml.jackson.databind.ObjectMapper
import java.sql.ResultSet
import java.util.UUID
import org.postgresql.util.PGobject
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject

class PostgresShoppingCartRepository(
    private val jdbcTemplate: JdbcTemplate, private val objectMapper: ObjectMapper
) : ShoppingCartRepository {
    override fun save(shoppingCart: ShoppingCart): Either<DomainError, ShoppingCart> {
        jdbcTemplate.update(
            """
            INSERT INTO shopping_cart VALUES(?, ?, ?)
        """.trimIndent(), shoppingCart.id.id, shoppingCart.userId.value, shoppingCart.toDatabaseModel()
        )

        return shoppingCart.right()
    }

    override fun findOrNew(shoppingCartId: ShoppingCartId, userId: UserId): ShoppingCart {
        return try {
            jdbcTemplate.queryForObject(
                """
                SELECT id, user_id, data FROM shopping_cart WHERE id = ? AND user_id = ? LIMIT 1
            """.trimIndent(), shoppingCartId.id, userId.value
            ) { rs: ResultSet, _: Any -> rs.resultSetToShoppingCart() }
        } catch (exception: EmptyResultDataAccessException) {
            ShoppingCart(shoppingCartId, userId, emptyList())
        }
    }

    private fun ShoppingCart.toDatabaseModel(): PGobject {
        val dbDto = PostgresShoppingCart(
            this.id.id,
            this.userId.value,
            this.products.toProductDatabaseModel()
        )
        val jsonObject = PGobject()
        jsonObject.type = "json"
        jsonObject.value = objectMapper.writeValueAsString(dbDto)
        return jsonObject
    }

    private fun List<ProductId>.toProductDatabaseModel(): List<String> {
        return this.map { it.value }
    }

    private fun ResultSet.resultSetToShoppingCart() = objectMapper
        .readValue(this.getObject("data").toString(), PostgresShoppingCart::class.java)
        .let {
            ShoppingCart(
                ShoppingCartId(it.id),
                UserId(it.userId),
                it.products.map { product -> ProductId(product) }
            )
        }
}

data class PostgresShoppingCart(
    val id: UUID,
    val userId: UUID,
    val products: List<String>
)
