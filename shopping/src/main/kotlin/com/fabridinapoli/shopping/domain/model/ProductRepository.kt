package com.fabridinapoli.shopping.domain.model

import arrow.core.Either

interface ProductRepository {
    fun find(): Either<DomainError, List<Product>>
    fun save(products: List<Product>)
}
