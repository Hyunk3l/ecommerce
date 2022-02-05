package com.fabridinapoli.shopping.infrastructure.configuration

import arrow.core.Either
import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartService
import com.fabridinapoli.shopping.application.service.SearchProductsService
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.DomainEvent
import com.fabridinapoli.shopping.domain.model.DomainEventPublisher
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.domain.model.ShoppingCart
import com.fabridinapoli.shopping.domain.model.ShoppingCartId
import com.fabridinapoli.shopping.domain.model.ShoppingCartRepository
import com.fabridinapoli.shopping.domain.model.UserId
import com.fabridinapoli.shopping.infrastructure.outbound.memory.InMemoryProductRepository
import com.fabridinapoli.shopping.infrastructure.outbound.memory.InMemoryUserRepository
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.InMemoryOutboxRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun outboxRepository() = InMemoryOutboxRepository()

    @Bean
    fun userRepository() = InMemoryUserRepository()

    @Bean
    fun productRepository() = InMemoryProductRepository()

    @Bean
    fun searchProductsService(productRepository: ProductRepository) = SearchProductsService(productRepository)

    @Bean
    fun addProductToShoppingCartService() = AddProductToShoppingCartService(
        object : ShoppingCartRepository {
            override fun save(shoppingCart: ShoppingCart): Either<DomainError, ShoppingCart> {
                TODO("Not yet implemented")
            }

            override fun findOrNew(shoppingCartId: ShoppingCartId, userId: UserId): ShoppingCart {
                TODO("Not yet implemented")
            }
        },
        object : DomainEventPublisher {
            override fun publish(domainEvent: DomainEvent) {
                TODO("Not yet implemented")
            }
        }
    )
}
