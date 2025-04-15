package com.ilsangtech.ilsang.core.data.user.datasource

import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse

interface UserDataSource {
    suspend fun login(loginRequest: LoginRequest): LoginResponse

    suspend fun getUserInfo(authorization: String): UserInfoResponse
}