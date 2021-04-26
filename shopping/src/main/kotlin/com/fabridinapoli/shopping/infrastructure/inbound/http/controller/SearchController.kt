package com.fabridinapoli.shopping.infrastructure.inbound.http.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController {
    @GetMapping("/products")
    fun getProducts(): ResponseEntity<String> {
        return ResponseEntity.ok("{}")
    }
}

class SearchProductsService {
    fun invoke(): SearchProductsResponse {
        TODO()
    }
}

data class SearchProductsResponse(val products: List<ProductResponse>)

data class ProductResponse(val id: String, val title: String, val price: Double)
