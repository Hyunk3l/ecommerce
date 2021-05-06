package com.fabridinapoli.shopping.application.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductRepository

class SearchProductsService(private val repository: ProductRepository) {
    fun invoke(): Either<DomainError, SearchProductsResponse> =
        repository
            .find()
            .fold({
                it.left()
            }, {
                it.toResponse().right()
            })

    private fun List<Product>.toResponse() =
        this.map {
            ProductResponse(it.id.value, it.title.value, it.price.value)
        }.let {
            SearchProductsResponse(it)
        }
}

data class SearchProductsResponse(val products: List<ProductResponse>)

data class ProductResponse(val id: String, val title: String, val price: Double)

data class DomainError(val message: String)
