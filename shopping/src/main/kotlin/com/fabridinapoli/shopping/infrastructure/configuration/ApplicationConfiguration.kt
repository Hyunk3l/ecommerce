package com.fabridinapoli.shopping.infrastructure.configuration

import com.fabridinapoli.shopping.application.service.SearchProductsService
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.infrastructure.outbound.memory.InMemoryProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun productRepository() = InMemoryProductRepository()

    @Bean
    fun searchProductsService(productRepository: ProductRepository) = SearchProductsService(productRepository)
}
