package com.fabridinapoli.shopping.infrastructure.outbound.database

import arrow.core.Either
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.domain.model.Title
import java.sql.ResultSet
import java.util.UUID
import org.springframework.jdbc.core.JdbcTemplate

class PostgresProductRepository(private val jdbcTemplate: JdbcTemplate) : ProductRepository {

    override fun find(): Either<DomainError, List<Product>> {
        return jdbcTemplate.query(
            """
            SELECT * FROM product
        """.trimIndent()
        ) { rs: ResultSet, _: Any -> rs.rsToProducts() }.right()
    }

    override fun save(products: List<Product>) {
        products.map { saveIntoDb(it) }
    }

    private fun saveIntoDb(product: Product) {
        jdbcTemplate.update(
            """
                INSERT INTO product VALUES(?, ?, ?)
            """.trimIndent(), product.id.value.toUUID(), product.title.value, product.price.value
        )
    }

    private fun String.toUUID() = UUID.fromString(this)

    private fun ResultSet.rsToProducts() = Product(
        id = ProductId(this.getString("id")),
        title = Title.from(this.getString("title")),
        price = Price.from(this.getBigDecimal("price").toDouble())
    )
}
