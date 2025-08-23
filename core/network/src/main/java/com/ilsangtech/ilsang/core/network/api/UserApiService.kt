package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.NicknameUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserCommercialPointResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageDeleteResponse
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserImageUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserInfoResponse
import com.ilsangtech.ilsang.core.network.model.user.UserIsZoneUpdateRequest
import com.ilsangtech.ilsang.core.network.model.user.UserPointResponse
import com.ilsangtech.ilsang.core.network.model.user.UserPointSummaryResponse
import com.ilsangtech.ilsang.core.network.model.user.UserTitleUpdateResponse
import com.ilsangtech.ilsang.core.network.model.user.UserXpStatsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserApiService {
    @GET("api/v1/user")
    suspend fun getUserInfo(
        @Query("id") id: String?
    ): UserInfoResponse

    @GET("api/v1/user/point")
    suspend fun getUserPoint(
        @Query("userId") userId: String?,
        @Query("seasonId") seasonId: Int?
    ): UserPointResponse

    @GET("api/v1/user/point/summary")
    suspend fun getUserPointSummary(
        @Query("seasonId") seasonId: Int
    ): UserPointSummaryResponse

    @GET("api/v1/user/point/commercial")
    suspend fun getUserCommercialPoint(
        @Query("userId") userId: String?
    ): UserCommercialPointResponse

    @GET("customer/xpStats")
    suspend fun getUserXpStats(
        @Query("customerId") customerId: String?
    ): UserXpStatsResponse

    @PUT("api/v1/user/profile/nickname")
    suspend fun updateUserNickname(
        @Body nicknameUpdateRequest: NicknameUpdateRequest
    ): NicknameUpdateResponse

    @PUT("customer/user/image")
    suspend fun updateUserImage(
        @Body imageUpdateRequest: UserImageUpdateRequest
    ): UserImageUpdateResponse

    @DELETE("customer/user/image")
    suspend fun deleteUserImage(): UserImageDeleteResponse

    @PUT("customer/user/title")
    suspend fun updateUserTitle(
        @Query("titleHistoryId") titleHistoryId: String
    ): UserTitleUpdateResponse

    @PUT("api/v1/user/profile/area-zone")
    suspend fun updateUserIsZone(
        @Body userIsZoneUpdateRequest: UserIsZoneUpdateRequest
    ): UserInfoResponse
}