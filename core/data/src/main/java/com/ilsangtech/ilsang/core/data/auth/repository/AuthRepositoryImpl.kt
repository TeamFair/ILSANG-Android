package com.ilsangtech.ilsang.core.data.auth.repository

import com.ilsangtech.ilsang.core.data.auth.datasource.AuthRemoteDataSource
import com.ilsangtech.ilsang.core.datastore.auth.AuthLocalDataSource
import com.ilsangtech.ilsang.core.domain.AuthRepository

class AuthRepositoryImpl(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(idToken: String): Result<Unit> {
        return runCatching {
            val (accessToken, refreshToken) = authRemoteDataSource.login(idToken)
            authLocalDataSource.saveAuthTokens(accessToken, refreshToken)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching { authRemoteDataSource.logout() }
    }

    override suspend fun withdraw(): Result<Unit> {
        return runCatching { authRemoteDataSource.withdraw() }
    }
}