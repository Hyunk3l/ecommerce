package com.fabridinapoli.shopping.infrastructure.inbound.http.controller

import com.fabridinapoli.shopping.application.service.ProductResponse
import com.fabridinapoli.shopping.application.service.SearchProductsResponse
import com.fabridinapoli.shopping.application.service.SearchProductsService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.json.CompareOrder
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import java.util.UUID
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@Tag("integration")
@WebFluxTest(controllers = [SearchController::class])
class SearchControllerShould {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockkBean
    private lateinit var searchProducts: SearchProductsService

    @Test
    fun `get a list of products`() {
        val searchProductsResponse = SearchProductsResponse(
            products = listOf(
                ProductResponse(
                    id = UUID.randomUUID().toString(),
                    title = "OWC Thunderbolt 3 Dock 14 Ports",
                    price = 239.00
                ),
                ProductResponse(
                    id = UUID.randomUUID().toString(),
                    title = "OWC Thunderbolt 4 Dock 11 Ports",
                    price = 269.00
                )
            )
        )
        every { searchProducts.invoke() } returns searchProductsResponse

        val response = webTestClient
            .get()
            .uri("/products")
            .exchange()

        response
            .expectStatus()
            .isOk
            .expectBody<String>()
            .returnResult().responseBody!!.shouldEqualJson("""
                {
                    "products": [
                        ${searchProductsResponse.products[0].toJson()},
                        ${searchProductsResponse.products[1].toJson()}
                    ]
                }
            """.trimIndent(), CompareOrder.Lenient)
    }

    private fun ProductResponse.toJson() = """
        {"id": "${this.id}", "title": "${this.title}", "price": ${this.price}}
    """.trimIndent()
}
