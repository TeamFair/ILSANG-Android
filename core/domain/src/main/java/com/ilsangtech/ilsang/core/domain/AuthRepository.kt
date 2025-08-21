package com.ilsangtech.ilsang.core.domain

interface AuthRepository {
    suspend fun login(idToken: String): Result<Unit>
}