package com.ilsangtech.ilsang.core.data.auth.repository

import com.ilsangtech.ilsang.core.data.auth.datasource.AuthRemoteDataSource
import com.ilsangtech.ilsang.core.datastore.auth.AuthLocalDataSource
import com.ilsangtech.ilsang.core.domain.AuthRepository

class AuthRepositoryImpl(
    private val userDataStore: UserDataStore,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun login(idToken: String): Result<Unit> {
        return runCatching {
            with(userDataStore) {
                setAccessToken(accessToken)
                refreshToken?.let { setRefreshToken(refreshToken) }
            }
        }
    }

    override suspend fun logout(): Result<Unit> {
        return runCatching { authRemoteDataSource.logout() }
    }

    override suspend fun withdraw(): Result<Unit> {
        return runCatching { authRemoteDataSource.withdraw() }
    }
}