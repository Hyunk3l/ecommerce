package com.fabridinapoli.shopping.infrastructure.outbound.memory

import com.fabridinapoli.shopping.domain.model.User
import com.fabridinapoli.shopping.domain.model.UserRepository

class InMemoryUserRepository : UserRepository {

    private var users = emptyList<User>()

    override fun save(user: User) {
        this.users = users + user
    }
}
