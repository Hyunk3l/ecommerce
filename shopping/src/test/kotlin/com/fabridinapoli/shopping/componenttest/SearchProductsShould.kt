package com.fabridinapoli.shopping.componenttest

import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.Title
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured.given
import java.util.UUID
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

private val PRODUCT_ID = UUID.randomUUID()
private val SECOND_PRODUCT_ID = UUID.randomUUID()

class SearchProductsShould : BaseComponentTest() {

    @DisplayName("""
        GIVEN a customer
        WHEN they search for products
        THEN should see a list of products
    """)
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
