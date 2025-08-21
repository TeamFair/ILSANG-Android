package com.ilsangtech.ilsang.core.data.auth.repository

import com.ilsangtech.ilsang.core.data.auth.datasource.AuthDataSource
import com.ilsangtech.ilsang.core.datastore.UserDataStore
import com.ilsangtech.ilsang.core.domain.AuthRepository

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val userDataStore: UserDataStore
) : AuthRepository {
    override suspend fun login(idToken: String): Result<Unit> {
        return runCatching {
            val (accessToken, refreshToken) = authDataSource.login(idToken)
            with(userDataStore) {
                setAccessToken(accessToken)
                refreshToken?.let { setRefreshToken(refreshToken) }
            }
        }
    }
}