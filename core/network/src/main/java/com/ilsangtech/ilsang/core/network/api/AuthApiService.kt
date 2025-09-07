package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginRequest
import com.ilsangtech.ilsang.core.network.model.auth.OAuthLoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("api/v1/open/login/oauth")
    suspend fun oAuthLogin(
        @Body oAuthLoginRequest: OAuthLoginRequest
    ): OAuthLoginResponse

    @POST("api/v1/logout")
    suspend fun logout()

    @POST("api/v1/withdraw")
    suspend fun withdraw()
}