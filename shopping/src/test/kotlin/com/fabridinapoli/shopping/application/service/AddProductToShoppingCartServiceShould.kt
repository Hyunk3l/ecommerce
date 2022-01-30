package com.fabridinapoli.shopping.application.service

import arrow.core.right
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
    "add a product to a new shopping cart" {
        val shoppingCartId = ShoppingCartId(UUID.randomUUID())
        val productId = ProductId(UUID.randomUUID().toString())
        val userId = UserId(UUID.randomUUID())
        val shoppingCartRepository = mockk<ShoppingCartRepository>()
        val domainEventPublisher = mockk<DomainEventPublisher>()
        val updatedShoppingCart = ShoppingCart(
            id = shoppingCartId,
            userId = userId,
            products = listOf(productId)
        )
        val event = ProductAddedToShoppingCartEvent(
            shoppingCartId = shoppingCartId,
            productId = productId
        )
        every { shoppingCartRepository.findOrNew(shoppingCartId, userId) } returns ShoppingCart(
            shoppingCartId,
            userId,
            emptyList()
        )
        every { shoppingCartRepository.save(updatedShoppingCart) } returns Unit.right()
        every { domainEventPublisher.publish(event) } returns Unit

        AddProductToShoppingCartService(shoppingCartRepository, domainEventPublisher)(
            AddProductToShoppingCartRequest(
                productId = productId.value,
                userId = userId.value.toString(),
                shoppingCartId = shoppingCartId.id.toString()
            )
        )

        verify(exactly = 1) { shoppingCartRepository.save(updatedShoppingCart) }
        verify(exactly = 1) { domainEventPublisher.publish(event) }
    }
})
