package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserApiService {
    @GET("customer/user")
    suspend fun getUserInfo(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String = ""
    ): UserInfoResponse
}