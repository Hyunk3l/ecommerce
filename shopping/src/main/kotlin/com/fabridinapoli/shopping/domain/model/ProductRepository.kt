package com.fabridinapoli.shopping.domain.model

interface ProductRepository {
    fun find(): List<Product>
}
