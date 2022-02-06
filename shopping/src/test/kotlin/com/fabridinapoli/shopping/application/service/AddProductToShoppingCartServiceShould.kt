package com.fabridinapoli.shopping.application.service

import arrow.core.left
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.DomainEventPublisher
import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCart
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.ShoppingCartRepository
import com.fabridinapoli.shopping.domain.model.UserId
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

class AddProductToShoppingCartServiceShould : StringSpec({

    val shoppingCartRepository = mockk<ShoppingCartRepository>()
    val domainEventPublisher = mockk<DomainEventPublisher>()
    val productId = ProductId(UUID.randomUUID().toString())
    val newShoppingCart = ShoppingCart(
        ShoppingCartId(UUID.randomUUID()),
        UserId(UUID.randomUUID()),
        emptyList()
    )
    val addProductToShoppingCartRequest = AddProductToShoppingCartRequest(
        productId = productId.value,
        userId = newShoppingCart.userId.value.toString(),
        shoppingCartId = newShoppingCart.id.id.toString()
    )
    val service = AddProductToShoppingCartService(shoppingCartRepository, domainEventPublisher)

    beforeTest {
        clearAllMocks()
    }

    "add a product to a new shopping cart" {
        val updatedShoppingCart = ShoppingCart(
            id = newShoppingCart.id,
            userId = newShoppingCart.userId,
            products = listOf(productId)
        )
        val event = ProductAddedToShoppingCartEvent(
            shoppingCartId = newShoppingCart.id,
            productId = productId
        )
        every { shoppingCartRepository.findOrNew(newShoppingCart.id, newShoppingCart.userId) } returns newShoppingCart
        every { shoppingCartRepository.save(updatedShoppingCart) } returns updatedShoppingCart.right()
        every { domainEventPublisher.publish(event) } returns Unit

        service(addProductToShoppingCartRequest)

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 1) { domainEventPublisher.publish(event) }
    }

    "fail if try to add a product that does not exist" {
        val updatedShoppingCart = ShoppingCart(
            id = newShoppingCart.id,
            userId = newShoppingCart.userId,
            products = listOf(productId)
        )
        every { shoppingCartRepository.findOrNew(newShoppingCart.id, newShoppingCart.userId) } returns newShoppingCart
        every { shoppingCartRepository.save(updatedShoppingCart) } returns DomainError("Product not found").left()

        service(addProductToShoppingCartRequest)

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 0) { domainEventPublisher.publish(any()) }
    }

    "fail if try to add more than max allowed number of products" {
        val existingShoppingCart = ShoppingCart(
            id = newShoppingCart.id,
            userId = newShoppingCart.userId,
            products = (1..15).map { productId }
        )
        every {
            shoppingCartRepository.findOrNew(newShoppingCart.id, newShoppingCart.userId)
        } returns existingShoppingCart

        service(addProductToShoppingCartRequest) shouldBe DomainError("Too many products. Max allowed is 15").left()

        verify(exactly = 0) { shoppingCartRepository.save(any()) }
        verify(exactly = 0) { domainEventPublisher.publish(any()) }
    }
})
