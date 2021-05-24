package com.fabridinapoli.shopping.application.service

import arrow.core.Either
import com.fabridinapoli.shopping.domain.model.DomainError

class AddProductToShoppingCartService {
    fun invoke(request: AddProductToShoppingCartRequest): Either<DomainError, Unit> {
        TODO()
    }
}

data class AddProductToShoppingCartRequest(val productId: String, val userId: String, val shoppingCartId: String)
