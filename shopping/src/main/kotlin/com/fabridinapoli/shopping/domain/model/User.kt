package com.fabridinapoli.shopping.domain.model

import java.util.UUID

data class User(val userId: UserId)

data class UserId(val value: UUID)
