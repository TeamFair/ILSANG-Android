package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.title.LegendTitleRankListResponse
import com.ilsangtech.ilsang.core.network.model.title.TitleDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.title.UserTitleNetworkModel
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TitleApiService {
    @GET("api/v1/title")
    suspend fun getTitleList(): List<TitleDetailNetworkModel>

    @GET("api/v1/user/title")
    suspend fun getUserTitleList(): List<UserTitleNetworkModel>

    @GET("api/v1/user/title/unread")
    suspend fun getUnreadTitleList(): List<UserTitleNetworkModel>

    @PUT("api/v1/user/title/{id}/read")
    suspend fun readTitle(@Path("id") titleHistoryId: Int)

    @GET("api/v1/user/title/legend")
    suspend fun getLegendTitleRankList(
        @Query("titleId") titleId: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): LegendTitleRankListResponse
}