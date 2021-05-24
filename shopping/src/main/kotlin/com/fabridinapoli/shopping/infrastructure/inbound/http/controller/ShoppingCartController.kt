package com.fabridinapoli.shopping.infrastructure.inbound.http.controller

import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartRequest
import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShoppingCartController(@Autowired private val addProductToShoppingCartService: AddProductToShoppingCartService) {
    @PutMapping("/shopping-carts/{shoppingCartId}")
    fun addProduct(
        @PathVariable shoppingCartId: String,
        @RequestBody requestBody: HttpAddProductRequestBody
    ): ResponseEntity<String> =
        requestBody.toUseCaseRequest(shoppingCartId)
            .let {
                addProductToShoppingCartService.invoke(it)
            }.fold(
                { ResponseEntity.status(400).build() },
                { ResponseEntity.status(201).build() }
            )

    private fun HttpAddProductRequestBody.toUseCaseRequest(shoppingCartId: String) =
        AddProductToShoppingCartRequest(
            shoppingCartId = shoppingCartId,
            productId = this.productId,
            userId = this.userId
        )
}

data class HttpAddProductRequestBody(
    val userId: String,
    val productId: String,
)
