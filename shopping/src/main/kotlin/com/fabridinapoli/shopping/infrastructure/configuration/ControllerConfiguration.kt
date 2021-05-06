package com.fabridinapoli.shopping.infrastructure.configuration

import com.fabridinapoli.shopping.application.service.SearchProductsService
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ControllerConfiguration {

    @Bean
    fun productRepository() = object : ProductRepository {
        override fun find(): List<Product> {
            TODO("Not yet implemented")
        }
    }

    @Bean
    fun searchProductsService(productRepository: ProductRepository) = SearchProductsService(productRepository)
}
