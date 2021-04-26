package com.fabridinapoli.shopping.infrastructure.configuration

import com.fabridinapoli.shopping.application.service.SearchProductsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ControllerConfiguration {
    @Bean
    fun searchProductsService() = SearchProductsService()
}
