package com.fabridinapoli.shopping.componenttest

import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.domain.model.Title
import com.fabridinapoli.shopping.infrastructure.ShoppingApplication
import com.fabridinapoli.shopping.infrastructure.outbound.memory.InMemoryProductRepository
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import java.util.UUID
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

private val PRODUCT_ID = UUID.randomUUID()
private val SECOND_PRODUCT_ID = UUID.randomUUID()

@Tag("component")
@Testcontainers
@SpringBootTest(classes = [ShoppingApplication::class], webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SearchProductsShould {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @LocalServerPort
    val servicePort: Int = 0

    companion object {
        @Container
        private val databaseContainer = PostgreSQLContainer<Nothing>("postgres:13.1")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", databaseContainer::getJdbcUrl)
            registry.add("spring.datasource.username", databaseContainer::getUsername)
            registry.add("spring.datasource.password", databaseContainer::getPassword)
        }
    }

    @Test
    fun `return a list of products`() {
        productRepository.save(listOf(
            Product(ProductId(PRODUCT_ID.toString()), Title("OWC Thunderbolt 3 Dock 14 Ports"), Price(239.00)),
            Product(ProductId(SECOND_PRODUCT_ID.toString()), Title("OWC Thunderbolt 4 Dock 11 Ports"), Price(269.00))
        ))

        val response = given()
            .contentType(ContentType.APPLICATION_JSON.toString())
            .port(servicePort)
            .`when`()
            .get("/products")
            .then()
            .extract()

        response.statusCode() shouldBe 200
        response.body().asString() shouldEqualJson """
                {
                    "products": [
                        {"id": "$PRODUCT_ID", "title": "OWC Thunderbolt 3 Dock 14 Ports", "price": 239.00},
                        {"id": "$SECOND_PRODUCT_ID", "title": "OWC Thunderbolt 4 Dock 11 Ports", "price": 269.00}
                    ]
                }
            """.trimIndent()
    }
}
