package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthRefreshResponse
import com.ilsangtech.ilsang.core.network.model.auth.WithdrawalResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v1/open/login/oauth")
    suspend fun oAuthLogin(
        @Body oAuthLoginRequest: OAuthLoginRequest
    ): OAuthLoginResponse

    @POST("open/login/oauth/refresh")
    suspend fun oAuthRefresh(
        @Body oAuthRefreshRequest: OAuthRefreshRequest
    ): OAuthRefreshResponse

    @GET("api/v1/logout")
    suspend fun logout()

    @GET("customer/withdraw")
    suspend fun withdraw(): WithdrawalResponse
}