package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("open/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}