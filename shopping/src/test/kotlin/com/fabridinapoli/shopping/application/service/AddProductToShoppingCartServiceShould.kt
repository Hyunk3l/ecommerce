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
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.UUID

class AddProductToShoppingCartServiceShould : StringSpec({

    val shoppingCartRepository = mockk<ShoppingCartRepository>()
    val domainEventPublisher = mockk<DomainEventPublisher>()

    "add a product to a new shopping cart" {
        val productId = ProductId(UUID.randomUUID().toString())
        val shoppingCart = ShoppingCart(
            ShoppingCartId(UUID.randomUUID()),
            UserId(UUID.randomUUID()),
            emptyList()
        )
        val updatedShoppingCart = ShoppingCart(
            id = shoppingCart.id,
            userId = shoppingCart.userId,
            products = listOf(productId)
        )
        val event = ProductAddedToShoppingCartEvent(
            shoppingCartId = shoppingCart.id,
            productId = productId
        )
        every { shoppingCartRepository.findOrNew(shoppingCart.id, shoppingCart.userId) } returns shoppingCart
        every { shoppingCartRepository.save(updatedShoppingCart) } returns updatedShoppingCart.right()
        every { domainEventPublisher.publish(event) } returns Unit

        AddProductToShoppingCartService(shoppingCartRepository, domainEventPublisher)(
            AddProductToShoppingCartRequest(
                productId = productId.value,
                userId = shoppingCart.userId.value.toString(),
                shoppingCartId = shoppingCart.id.id.toString()
            )
        )

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 1) { domainEventPublisher.publish(event) }
    }

    "fail if try to add a product that does not exist" {
        val productId = ProductId(UUID.randomUUID().toString())
        val shoppingCart = ShoppingCart(
            ShoppingCartId(UUID.randomUUID()),
            UserId(UUID.randomUUID()),
            emptyList()
        )
        val updatedShoppingCart = ShoppingCart(
            id = shoppingCart.id,
            userId = shoppingCart.userId,
            products = listOf(productId)
        )
        val event = ProductAddedToShoppingCartEvent(
            shoppingCartId = shoppingCart.id,
            productId = productId
        )
        every { shoppingCartRepository.findOrNew(shoppingCart.id, shoppingCart.userId) } returns shoppingCart
        every { shoppingCartRepository.save(updatedShoppingCart) } returns DomainError("Product not found").left()

        AddProductToShoppingCartService(shoppingCartRepository, domainEventPublisher)(
            AddProductToShoppingCartRequest(
                productId = productId.value,
                userId = shoppingCart.userId.value.toString(),
                shoppingCartId = shoppingCart.id.id.toString()
            )
        )

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 0) { domainEventPublisher.publish(event) }
    }
})
