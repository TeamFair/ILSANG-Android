package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse

interface UserDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
}