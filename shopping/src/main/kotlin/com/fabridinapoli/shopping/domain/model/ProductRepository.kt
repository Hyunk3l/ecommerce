package com.fabridinapoli.shopping.domain.model

import arrow.core.Either
import com.fabridinapoli.shopping.application.service.DomainError

interface ProductRepository {
    fun find(): Either<DomainError, List<Product>>
}
