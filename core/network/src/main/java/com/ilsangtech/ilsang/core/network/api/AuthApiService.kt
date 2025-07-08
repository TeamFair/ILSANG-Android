package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshResponse
import com.ilsangtech.ilsang.core.network.model.auth.LogoutResponse
import com.ilsangtech.ilsang.core.network.model.auth.WithdrawalResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("open/login/oauth")
    suspend fun oAuthLogin(
        @Body oAuthLoginRequest: OAuthLoginRequest
    ): OAuthLoginResponse

    @POST("open/login/oauth/refresh")
    suspend fun oAuthRefresh(
        @Body oAuthRefreshRequest: OAuthRefreshRequest
    ): OAuthRefreshResponse

    @GET("customer/logout")
    suspend fun logout(
        @Header("Authorization") accessToken: String
    ): LogoutResponse

    @GET("customer/withdraw")
    suspend fun withdraw(
        @Header("Authorization") accessToken: String
    ): WithdrawalResponse
}