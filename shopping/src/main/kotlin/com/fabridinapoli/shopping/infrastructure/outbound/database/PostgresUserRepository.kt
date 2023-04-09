package com.fabridinapoli.shopping.infrastructure.outbound.database

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.fabridinapoli.shopping.domain.model.DomainError
import com.fabridinapoli.shopping.domain.model.User
import com.fabridinapoli.shopping.domain.model.UserRepository
import org.springframework.jdbc.core.JdbcTemplate

class PostgresUserRepository(private val jdbcTemplate: JdbcTemplate) : UserRepository {
    override fun save(user: User) {
        jdbcTemplate.update(
            """
            INSERT INTO users VALUES('a', 'b', 'c')
        """.trimIndent()
        )
    }

    fun saveEither(): Either<DomainError, Unit> {
        return try {
            jdbcTemplate.update(
                """
                    INSERT INTO users VALUES('a', 'b', 'c')
                """.trimIndent()
            )
            Unit.right()
        } catch (exception: Exception) {
            DomainError(message = "Database error").left()
        }
    }
}
