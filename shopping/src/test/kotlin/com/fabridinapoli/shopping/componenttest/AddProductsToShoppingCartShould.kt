package com.fabridinapoli.shopping.componenttest

import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.Title
import com.fabridinapoli.shopping.domain.model.User
import com.fabridinapoli.shopping.domain.model.UserId
import com.fabridinapoli.shopping.domain.model.UserRepository
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxRepository
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxShoppingCartEvent
import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import java.util.UUID
import org.apache.http.entity.ContentType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

private val SHOPPING_CART_ID = UUID.randomUUID()
private val USER_ID = UUID.randomUUID()
private val PRODUCT_ID = UUID.randomUUID()

class AddProductsToShoppingCartShould : BaseComponentTest() {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var outboxRepository: OutboxRepository

    @DisplayName(
        """
        GIVEN a customer
        WHEN they add a product into a shopping cart
        THEN the product should be added successfully
    """
    )
    @Test
    fun `add a product to shopping cart successfully`() {
        userRepository.save(User(UserId(USER_ID)))
        productRepository.save(listOf(
            Product(ProductId(PRODUCT_ID.toString()), Title("OWC Thunderbolt 3 Dock 14 Ports"), Price(239.00)),
        ))

        val response = RestAssured.given()
            .contentType(ContentType.APPLICATION_JSON.toString())
            .port(servicePort)
            .and()
            .body("""
                {
                    "userId": "$USER_ID",
                    "productId: "$PRODUCT_ID",
                }
            """.trimIndent())
            .`when`()
            .put("/shopping-carts/$SHOPPING_CART_ID")
            .then()
            .extract()

        response.statusCode() shouldBe 201
        outboxRepository.findLatestBy(ShoppingCartId(SHOPPING_CART_ID)
            ) shouldBe OutboxShoppingCartEvent(id = SHOPPING_CART_ID.toString(), type = ProductAddedToShoppingCartEvent::class.toString())
    }
}
