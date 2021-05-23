package com.fabridinapoli.shopping.domain.model

interface UserRepository {
    fun save(user: User)
}
