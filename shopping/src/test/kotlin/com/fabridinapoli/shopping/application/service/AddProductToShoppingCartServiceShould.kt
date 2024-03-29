package com.fabridinapoli.shopping.application.service

import arrow.core.left
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.DomainEvent
import com.fabridinapoli.shopping.domain.model.DomainEventPublisher
import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.ShoppingCartRepository
import com.fabridinapoli.shopping.domain.model.UserId
import com.fabridinapoli.shopping.domain.model.buildShoppingCart
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
    val shoppingCartId = ShoppingCartId(UUID.randomUUID())
    val userId = UserId(UUID.randomUUID())
    val newShoppingCart = buildShoppingCart(
        id = shoppingCartId,
        userId = userId,
    )
    val addProductToShoppingCartRequest = AddProductToShoppingCartRequest(
        productId = productId.value,
        userId = userId.value.toString(),
        shoppingCartId = shoppingCartId.id.toString(),
    )
    val service = AddProductToShoppingCartService(shoppingCartRepository, domainEventPublisher)

    beforeTest {
        clearAllMocks()
    }

    "add a product to a new shopping cart" {
        val updatedShoppingCart = buildShoppingCart(
            id = shoppingCartId,
            userId = userId,
            products = listOf(productId)
        )
        val event = ProductAddedToShoppingCartEvent(
            shoppingCartId =  shoppingCartId,
            productId = productId
        )
        every { shoppingCartRepository.findOrNew(shoppingCartId, userId) } returns newShoppingCart
        every { shoppingCartRepository.save(updatedShoppingCart) } returns updatedShoppingCart.right()
        every { domainEventPublisher.publish(event) } returns Unit

        service(addProductToShoppingCartRequest)

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 1) { domainEventPublisher.publish(event) }
    }

    "fail if try to add a product that does not exist" {
        val updatedShoppingCart = buildShoppingCart(
            id = shoppingCartId,
            userId = userId,
            products = listOf(productId),
        )
        every { shoppingCartRepository.findOrNew(shoppingCartId, userId) } returns newShoppingCart
        every { shoppingCartRepository.save(updatedShoppingCart) } returns DomainError("Product not found").left()

        service(addProductToShoppingCartRequest)

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 0) { domainEventPublisher.publish(ProductAddedToShoppingCartEvent(
            shoppingCartId = shoppingCartId,
            productId = productId
        )) }
    }

    "fail if try to add more than max allowed number of products" {
        val existingShoppingCart = buildShoppingCart(products = (1..15).map { productId })
        every {
            shoppingCartRepository.findOrNew(newShoppingCart.id, newShoppingCart.userId)
        } returns existingShoppingCart

        service(addProductToShoppingCartRequest) shouldBe DomainError("Too many products. Max allowed is 15").left()

        verify(exactly = 0) { shoppingCartRepository.save(any()) }
        verify(exactly = 0) { domainEventPublisher.publish(ProductAddedToShoppingCartEvent(
            shoppingCartId = shoppingCartId,
            productId = productId
        )) }
    }
})
