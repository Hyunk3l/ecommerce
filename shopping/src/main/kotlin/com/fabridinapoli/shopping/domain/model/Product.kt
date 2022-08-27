package com.fabridinapoli.shopping.domain.model

import java.util.UUID

data class Product(val id: ProductId, val title: Title, val price: Price)

data class ProductId(val value: String) {
    companion object {
        fun from(value: UUID) = ProductId(value.toString())
    }
}

data class Title(val value: String) {
    companion object {
        fun from(value: String) = Title(value)
    }
}

data class Price(val value: Double) {
    companion object {
        fun from(value: Double) = Price(value)
    }
}
