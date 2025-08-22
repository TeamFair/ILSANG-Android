package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.rank.CommercialAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.CommercialTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.MetroAreaRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.MetroTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RankApiService {
    @GET("api/v1/rank/user/total")
    suspend fun getTotalTopRankUsers(
        @Query("commercialAreaCode") commercialAreaCode: String
    ): List<UserRankNetworkModel>

    @GET("api/v1/rank/user/metro")
    suspend fun getMetroTopRankUsers(
        @Query("seasonId") seasonId: Int?,
        @Query("metroAreaCode") metroAreaCode: String
    ): MetroTopRankUsersResponse

    @GET("api/v1/rank/user/commercial")
    suspend fun getCommercialTopRankUsers(
        @Query("seasonId") seasonId: Int?,
        @Query("commercialAreaCode") commercialAreaCode: String
    ): CommercialTopRankUsersResponse

    @GET("api/v1/rank/user/contribution")
    suspend fun getContributionTopRankUsers(
        @Query("seasonId") seasonId: Int?
    ): List<UserRankNetworkModel>

    @GET("api/v1/rank/area/metro")
    suspend fun getMetroTopRankAreas(
        @Query("seasonId") seasonId: Int?
    ): List<MetroAreaRankNetworkModel>

    @GET("api/v1/rank/area/commercial")
    suspend fun getCommercialTopRankAreas(
        @Query("seasonId") seasonId: Int?
    ): List<CommercialAreaRankNetworkModel>

    @GET("open/v1/rank/top-users")
    suspend fun getTopRankUsers(): TopUsersResponse

    @GET("customer/rank")
    suspend fun getXpTypeRank(
        @Query("xpType") xpType: String,
        @Query("size") size: Int
    ): XpTypeRankResponse
}