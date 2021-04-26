package com.fabridinapoli.shopping.application.service

class SearchProductsService {
    fun invoke(): SearchProductsResponse {
        TODO()
    }
}

data class SearchProductsResponse(val products: List<ProductResponse>)

data class ProductResponse(val id: String, val title: String, val price: Double)
