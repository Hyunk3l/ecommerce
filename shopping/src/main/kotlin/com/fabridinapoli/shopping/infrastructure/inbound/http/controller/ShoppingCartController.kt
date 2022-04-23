package com.fabridinapoli.shopping.infrastructure.inbound.http.controller

import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartRequest
import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartService
import com.fabridinapoli.shopping.domain.model.DomainError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShoppingCartController(
    @Autowired private val addProductToShoppingCartService: AddProductToShoppingCartService
) {
    @PutMapping(path = ["/shopping-carts/{shoppingCartId}"], consumes = ["application/json"])
    fun addProduct(
        @PathVariable shoppingCartId: String,
        @RequestBody requestBody: HttpAddProductRequestBody
    ): ResponseEntity<String> =
        requestBody.toUseCaseRequest(shoppingCartId)
            .let {
                addProductToShoppingCartService.invoke(it)
            }.fold(
                { ResponseEntity.status(400).body(it.toErrorBody()) },
                { ResponseEntity.status(201).build() }
            )

    private fun HttpAddProductRequestBody.toUseCaseRequest(shoppingCartId: String) =
        AddProductToShoppingCartRequest(
            shoppingCartId = shoppingCartId,
            productId = this.productId,
            userId = this.userId
        )

    private fun DomainError.toErrorBody() =
        """
            {
                "error": "Cannot add the product to the shopping cart",
                "details": [
                    "${this.message}"
                ]
            }
        """.trimIndent()
}

data class HttpAddProductRequestBody(
    val userId: String,
    val productId: String,
)
