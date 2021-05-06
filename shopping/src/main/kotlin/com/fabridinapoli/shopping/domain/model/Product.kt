package com.fabridinapoli.shopping.domain.model

data class Product(val id: ProductId, val title: Title, val price: Price)

data class ProductId(val value: String)

data class Title(val value: String)

data class Price(val value: Double)
