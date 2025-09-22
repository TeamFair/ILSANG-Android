package com.ilsangtech.ilsang.core.data.auth.datasource

import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse

class AuthRemoteDataSourceImpl(private val authApiService: AuthApiService) : AuthRemoteDataSource {
    override suspend fun login(idToken: String): OAuthLoginResponse {
        return authApiService.oAuthLogin(OAuthLoginRequest(idToken = idToken))
    }

    override suspend fun logout() {
        return authApiService.logout()
    }

    override suspend fun withdraw() {
        return authApiService.withdraw()
    }
}