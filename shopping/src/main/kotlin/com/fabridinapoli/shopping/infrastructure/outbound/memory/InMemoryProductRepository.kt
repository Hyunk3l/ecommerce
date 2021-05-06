package com.fabridinapoli.shopping.infrastructure.outbound.memory

import arrow.core.Either
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductRepository

class InMemoryProductRepository : ProductRepository {
    private var products: List<Product> = emptyList()

    override fun find(): Either<DomainError, List<Product>> =
        this.products.right()

    // We don't care about thread safety.
    override fun save(products: List<Product>) {
        this.products = this.products + products
    }
}
