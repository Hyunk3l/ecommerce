package com.fabridinapoli.shopping.infrastructure.inbound.http.controller

import com.fabridinapoli.shopping.application.service.ProductResponse
import com.fabridinapoli.shopping.application.service.SearchProductsResponse
import com.fabridinapoli.shopping.application.service.SearchProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController {

    @Autowired
    private lateinit var searchProductsService: SearchProductsService

    @GetMapping("/products")
    fun getProducts(): ResponseEntity<HttpSearchProductResponse> =
        searchProductsService
            .invoke()
            .let { it.toHttpResponse() }
            .let { ResponseEntity.ok(it) }

    private fun SearchProductsResponse.toHttpResponse() =
        HttpSearchProductResponse(products = this.products.toHttpProduct())

    private fun List<ProductResponse>.toHttpProduct() =
        this.map { HttpProduct(it.id, it.title, it.price) }
}

data class HttpSearchProductResponse(val products: List<HttpProduct>)

data class HttpProduct(val id: String, val title: String, val price: Double)
