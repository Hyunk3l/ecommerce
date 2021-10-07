package com.fabridinapoli.shopping.infrastructure.configuration

import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartService
import com.fabridinapoli.shopping.application.service.SearchProductsService
import com.fabridinapoli.shopping.domain.model.ProductRepository
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
    fun addProductToShoppingCartService() = AddProductToShoppingCartService()
}
