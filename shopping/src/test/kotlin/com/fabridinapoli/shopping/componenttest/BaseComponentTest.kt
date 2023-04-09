package com.fabridinapoli.shopping.componenttest

import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.ProductRepository
import com.fabridinapoli.shopping.infrastructure.ShoppingApplication
import org.junit.jupiter.api.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Testcontainers

@Tag("component")
@Testcontainers
@SpringBootTest(classes = [ShoppingApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseComponentTest {
    @LocalServerPort
    val servicePort: Int = 0

    @Autowired
    internal lateinit var productRepository: ProductRepository

    companion object {

        private val databaseContainer = DatabaseContainer()

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", databaseContainer.postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", databaseContainer.postgresContainer::getUsername)
            registry.add("spring.datasource.password", databaseContainer.postgresContainer::getPassword)
            registry.add("spring.flyway.url", databaseContainer.postgresContainer::getJdbcUrl)
            registry.add("spring.flyway.password", databaseContainer.postgresContainer::getPassword)
            registry.add("spring.flyway.username", databaseContainer.postgresContainer::getUsername)
        }
    }
}
