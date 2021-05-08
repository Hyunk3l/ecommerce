package com.fabridinapoli.shopping.componenttest

import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.infrastructure.ShoppingApplication
import org.junit.jupiter.api.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Tag("component")
@Testcontainers
@SpringBootTest(classes = [ShoppingApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class BaseComponentTest {
    @LocalServerPort
    val servicePort: Int = 0

    @Autowired
    internal lateinit var productRepository: ProductRepository

    companion object {
        @Container
        private val databaseContainer = PostgreSQLContainer<Nothing>("postgres:13.1")

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", databaseContainer::getJdbcUrl)
            registry.add("spring.datasource.username", databaseContainer::getUsername)
            registry.add("spring.datasource.password", databaseContainer::getPassword)
        }
    }
}
