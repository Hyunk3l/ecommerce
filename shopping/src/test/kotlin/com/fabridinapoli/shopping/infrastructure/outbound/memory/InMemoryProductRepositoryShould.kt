package com.fabridinapoli.shopping.infrastructure.outbound.memory

import arrow.core.right
import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.Title
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

class InMemoryProductRepositoryShould : StringSpec({

    "find a list of products" {
        val firstProductId = UUID.randomUUID().toString()
        val firstProductTitle = UUID.randomUUID().toString()
        val firstProductPrice = 20.10
        val secondProductId = UUID.randomUUID().toString()
        val secondProductTitle = UUID.randomUUID().toString()
        val secondProductPrice = 10.99
        val products = listOf(
            Product(ProductId(firstProductId), Title(firstProductTitle), Price(firstProductPrice)),
            Product(ProductId(secondProductId), Title(secondProductTitle), Price(secondProductPrice))
        )
        val repository = InMemoryProductRepository()
        repository.save(products)

        repository.find() shouldBe products.right()
    }
})
