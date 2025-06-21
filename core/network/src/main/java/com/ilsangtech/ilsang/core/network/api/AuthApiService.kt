package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.auth.LoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.LoginResponse
import com.ilsangtech.ilsang.core.network.model.auth.LogoutResponse
import com.ilsangtech.ilsang.core.network.model.auth.WithdrawalResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("open/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("customer/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String
    ): LogoutResponse

    @GET("customer/withdraw")
    suspend fun withdraw(
        @Header("Authorization") accessToken: String
    ): WithdrawalResponse
}