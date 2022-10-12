package com.fabridinapoli.shopping.infrastructure.service

import arrow.core.Either
import com.fabridinapoli.shopping.domain.model.DomainError
import org.springframework.transaction.support.AbstractPlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

class TransactionalUseCase(
    private val transactionManager: AbstractPlatformTransactionManager
) {
    operator fun <T> invoke(block: () -> Either<DomainError, T>): Either<DomainError, T> {
        val transactionTemplate = TransactionTemplate(transactionManager)
        val transactionStatus = transactionManager.getTransaction(transactionTemplate)
        return transactionTemplate.execute {
            block()
                .mapLeft {
                    transactionManager.rollback(transactionStatus)
                    it
                }
        }!!
    }
}
