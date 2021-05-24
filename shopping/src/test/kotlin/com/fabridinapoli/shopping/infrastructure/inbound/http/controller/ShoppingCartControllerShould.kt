package com.fabridinapoli.shopping.infrastructure.inbound.http.controller

import arrow.core.Either
import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartRequest
import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import java.util.UUID
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

private val SHOPPING_CART_ID = UUID.randomUUID()
private val PRODUCT_ID = UUID.randomUUID()
private val USER_ID = UUID.randomUUID()

@Tag("integration")
@WebFluxTest(controllers = [ShoppingCartController::class])
class ShoppingCartControllerShould {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockkBean
    private lateinit var addProductToShoppingCartService: AddProductToShoppingCartService

    @Test
    fun `add a product to a shopping cart`() {
        val request = AddProductToShoppingCartRequest(
            shoppingCartId = SHOPPING_CART_ID.toString(),
            productId = PRODUCT_ID.toString(),
            userId = USER_ID.toString(),
        )
        every { addProductToShoppingCartService.invoke(request) } returns Either.Right(Unit)

        val response = webTestClient
            .put()
            .uri("/shopping-carts/$SHOPPING_CART_ID")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                """
                {
                    "userId": "$USER_ID",
                    "productId": "$PRODUCT_ID"
                }
            """.trimIndent()
            )
            .exchange()

        response
            .expectStatus()
            .isCreated
            .expectBody()
            .isEmpty
        verify { addProductToShoppingCartService.invoke(request) }
    }
}
