package com.fabridinapoli.shopping.application.service

import arrow.core.right
import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.domain.model.Title
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.UUID

class SearchProductsServiceShould : StringSpec({

    val repository = mockk<ProductRepository>()

    "return a list of unordered products" {
        val firstProductId = UUID.randomUUID().toString()
        val firstProductTitle = UUID.randomUUID().toString()
        val firstProductPrice = 20.10
        val secondProductId = UUID.randomUUID().toString()
        val secondProductTitle = UUID.randomUUID().toString()
        val secondProductPrice = 10.99
        every { repository.find() } returns listOf(
            Product(ProductId(firstProductId), Title(firstProductTitle), Price(firstProductPrice)),
            Product(ProductId(secondProductId), Title(secondProductTitle), Price(secondProductPrice)),
        )

        val response = SearchProductsService(repository).invoke()

        response shouldBe SearchProductsResponse(
            listOf(
                ProductResponse(firstProductId, firstProductTitle, firstProductPrice),
                ProductResponse(secondProductId, secondProductTitle, secondProductPrice),
            )
        ).right()
    }


    "return an empty list of products" {
        every { repository.find() } returns emptyList()

        val response = SearchProductsService(repository).invoke()

        response shouldBe SearchProductsResponse(emptyList()).right()
    }
})
