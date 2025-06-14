package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApiService {
    @GET("customer/user")
    suspend fun getUserInfo(
        @Header("authorization") authorization: String,
        @Query("customerId") customerId: String?
    ): UserInfoResponse

    @GET("customer/xpStats")
    suspend fun getUserXpStats(
        @Header("authorization") authorization: String,
        @Query("customerId") customerId: String?
    ): UserXpStatsResponse

    @PUT("customer/user")
    suspend fun updateUserNickname(
        @Header("authorization") authorization: String,
        @Body nicknameUpdateRequest: NicknameUpdateRequest
    ): NicknameUpdateResponse

    @PUT("customer/user/image")
    suspend fun updateUserImage(
        @Header("authorization") authorization: String,
        @Body imageUpdateRequest: UserImageUpdateRequest
    ): UserImageUpdateResponse

    @DELETE("customer/user/image")
    suspend fun deleteUserImage(
        @Header("authorization") authorization: String
    ): UserImageDeleteResponse
}