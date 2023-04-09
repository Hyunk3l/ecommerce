package com.fabridinapoli.shopping.infrastructure.service

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.fabridinapoli.shopping.DatabaseContainer
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.Price
import com.fabridinapoli.shopping.domain.model.Product
import com.fabridinapoli.shopping.domain.model.ProductId
import com.fabridinapoli.shopping.domain.model.Title
import com.fabridinapoli.shopping.infrastructure.outbound.database.PostgresProductRepository
import com.fabridinapoli.shopping.infrastructure.outbound.database.PostgresUserRepository
import io.kotest.matchers.shouldBe
import java.util.UUID
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.support.JdbcTransactionManager

@Tag("integration")
class TransactionalUseCaseShould {

    private val databaseContainer = DatabaseContainer()

    private val jdbcTemplate = JdbcTemplate(databaseContainer.dataSource)

    private val transactionManager = JdbcTransactionManager(databaseContainer.dataSource)

    private val userRepository = PostgresUserRepository(jdbcTemplate)

    private val productRepository = PostgresProductRepository(jdbcTemplate)

    @Test
    fun `run a use case successfully`() {
        val transactionalUseCase = TransactionalUseCase(transactionManager)
        val useCase = SomeUseCase()
        val id = UUID.randomUUID().toString()

        val response = transactionalUseCase.invoke { useCase(SomeUseCaseRequest(id)) }

        response shouldBe SomeUseCaseResponse(id).right()
    }

    @Test
    fun `run a use case that returns unit, successfully`() {
        val transactionalUseCase = TransactionalUseCase(transactionManager)
        val useCase = SomeUseCaseReturningUnit()

        val response = transactionalUseCase.invoke { useCase() }

        response shouldBe Unit.right()
    }

    @Test
    fun `run a use case that returns domain error`() {
        val transactionalUseCase = TransactionalUseCase(transactionManager)
        val useCase = SomeUseCaseReturningDomainError()

        val response = transactionalUseCase.invoke { useCase() }

        response shouldBe DomainError("error").left()
    }

    @Test
    fun `run a use case that throws a sql exception`() {
        val transactionalUseCase = TransactionalUseCase(transactionManager)
        val useCase = SomeUseCaseUsingDbThrowingException(productRepository)

        val response = transactionalUseCase { useCase() }

        response shouldBe DomainError("error").left()
    }

    @Test
    fun `run a use case that returns a domain error`() {
        val transactionalUseCase = TransactionalUseCase(transactionManager)
        val useCase = SomeUseCaseUsingDbReturningDomainError(userRepository)

        val response = transactionalUseCase { useCase() }

        response shouldBe DomainError("Database error").left()
    }

    class SomeUseCase {
        operator fun invoke(request: SomeUseCaseRequest): Either<DomainError, SomeUseCaseResponse> {
            return SomeUseCaseResponse(request.id).right()
        }
    }

    data class SomeUseCaseRequest(val id: String)
    data class SomeUseCaseResponse(val id: String)

    class SomeUseCaseReturningUnit {
        operator fun invoke(): Either<DomainError, Unit> {
            return Unit.right()
        }
    }

    class SomeUseCaseReturningDomainError {
        operator fun invoke(): Either<DomainError, Unit> {
            return DomainError("error").left()
        }
    }

    class SomeUseCaseUsingDbThrowingException(private val repository: PostgresProductRepository) {
        operator fun invoke(): Either<DomainError, Unit> {
            val product = Product(ProductId.from(UUID.randomUUID()), Title.from("aaa"), Price.from(10.0))
            try {
                repository.save(listOf(product, product))
            } catch (exception: Exception) {
                return DomainError("error").left()
            }
            return Unit.right()
        }
    }

    class SomeUseCaseUsingDbReturningDomainError(private val repository: PostgresUserRepository) {
        operator fun invoke(): Either<DomainError, Unit> {
            return repository.saveEither()
        }
    }
}
