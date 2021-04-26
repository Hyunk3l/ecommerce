package com.fabridinapoli.shopping.componenttest

import com.fabridinapoli.shopping.infrastructure.ShoppingApplication
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import java.util.UUID
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
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

@Disabled
@Tag("component")
@Testcontainers
@SpringBootTest(classes = [ShoppingApplication::class], webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SearchProductsShould {

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
        val response = given()
            .contentType(ContentType.APPLICATION_JSON.toString())
            .port(servicePort)
            .`when`()
            .get("/products")
            .then()
            .extract()

        response.statusCode() shouldBe 200
        response.contentType() shouldBe ContentType.APPLICATION_JSON.toString()
        response.body() shouldBe """
                {
                    "products": [
                        {"id": "$PRODUCT_ID", "title": "OWC Thunderbolt 3 Dock 14 Ports", "price": 239.00},
                        {"id": "$SECOND_PRODUCT_ID", "title": "OWC Thunderbolt 4 Dock 11 Ports", "price": 269.00},
                    ]
                }
            """.trimIndent()
    }
}
