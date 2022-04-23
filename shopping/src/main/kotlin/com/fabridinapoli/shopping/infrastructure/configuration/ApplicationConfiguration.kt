package com.fabridinapoli.shopping.infrastructure.configuration

import com.fabridinapoli.shopping.application.service.AddProductToShoppingCartService
import com.fabridinapoli.shopping.application.service.SearchProductsService
import com.fabridinapoli.shopping.domain.model.DomainEventPublisher
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.domain.model.ShoppingCartRepository
import com.fabridinapoli.shopping.infrastructure.outbound.database.DefaultOutboxDomainEventPublisher
import com.fabridinapoli.shopping.infrastructure.outbound.database.PostgresOutboxRepository
import com.fabridinapoli.shopping.infrastructure.outbound.database.PostgresShoppingCartRepository
import com.fabridinapoli.shopping.infrastructure.outbound.memory.InMemoryProductRepository
import com.fabridinapoli.shopping.infrastructure.outbound.memory.InMemoryUserRepository
import com.fabridinapoli.shopping.infrastructure.outbound.outbox.OutboxRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class ApplicationConfiguration {

    @Bean
    fun outboxRepository(jdbcTemplate: JdbcTemplate, objectMapper: ObjectMapper) =
        PostgresOutboxRepository(jdbcTemplate, objectMapper)

    @Bean
    fun userRepository() = InMemoryUserRepository() //TODO: implement postgres user repo

    @Bean
    fun productRepository() = InMemoryProductRepository() //TODO: implement postgres product repo

    @Bean
    fun searchProductsService(productRepository: ProductRepository) = SearchProductsService(productRepository)

    @Bean
    fun objectMapper() = ObjectMapper().registerKotlinModule()

    @Bean
    fun domainEventPublisher(outboxRepository: OutboxRepository) = DefaultOutboxDomainEventPublisher(
        outboxRepository = outboxRepository
    )

    @Bean
    fun postgresShoppingCartRepository(jdbcTemplate: JdbcTemplate, objectMapper: ObjectMapper) =
        PostgresShoppingCartRepository(jdbcTemplate = jdbcTemplate, objectMapper = objectMapper)

    @Bean
    fun addProductToShoppingCartService(
        shoppingCartRepository: ShoppingCartRepository,
        domainEventPublisher: DomainEventPublisher
    ) = AddProductToShoppingCartService(
            shoppingCartRepository = shoppingCartRepository,
            domainEventPublisher = domainEventPublisher
        )
}
