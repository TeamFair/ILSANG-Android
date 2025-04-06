package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.api.AuthApiService
import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : UserDataSource {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authApiService.login(loginRequest)
    }
}
