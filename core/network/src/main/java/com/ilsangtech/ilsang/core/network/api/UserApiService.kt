package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApiService {
    @GET("customer/user")
    suspend fun getUserInfo(
        @Header("authorization") authorization: String,
        @Query("userId") userId: String = ""
    ): UserInfoResponse

    @PUT("customer/user")
    suspend fun updateUserNickname(
        @Header("authorization") authorization: String,
        @Body nicknameUpdateRequest: NicknameUpdateRequest
    ): NicknameUpdateResponse
}