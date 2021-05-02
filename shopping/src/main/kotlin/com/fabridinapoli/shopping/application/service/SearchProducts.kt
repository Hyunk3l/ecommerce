package com.fabridinapoli.shopping.application.service

import arrow.core.Either

class SearchProductsService {
    fun invoke(): Either<DomainError, SearchProductsResponse> {
        TODO()
    }
}

data class SearchProductsResponse(val products: List<ProductResponse>)

data class ProductResponse(val id: String, val title: String, val price: Double)

data class DomainError(val message: String)
