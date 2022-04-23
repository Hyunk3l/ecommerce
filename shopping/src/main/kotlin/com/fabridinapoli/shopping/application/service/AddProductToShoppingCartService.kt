package com.fabridinapoli.shopping.application.service

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.DomainEventPublisher
import com.fabridinapoli.shopping.domain.model.ProductAddedToShoppingCartEvent
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.ShoppingCartRepository
import com.fabridinapoli.shopping.domain.model.UserId
import org.springframework.transaction.annotation.Transactional

open class AddProductToShoppingCartService(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val domainEventPublisher: DomainEventPublisher
) {
    @Transactional
    operator fun invoke(request: AddProductToShoppingCartRequest): Either<DomainError, Unit> =
        shoppingCartRepository
            .findOrNew(ShoppingCartId.from(request.shoppingCartId), UserId.from(request.userId))
            .addProduct(ProductId(request.productId))
            .flatMap(shoppingCartRepository::save)
            .map {
                domainEventPublisher.publish(
                    ProductAddedToShoppingCartEvent(it.id, ProductId(request.productId))
                )
                Unit.right()
            }
}

data class AddProductToShoppingCartRequest(val productId: String, val userId: String, val shoppingCartId: String)
