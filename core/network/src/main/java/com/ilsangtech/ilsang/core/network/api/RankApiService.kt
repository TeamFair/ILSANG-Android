package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.TotalTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RankApiService {
    @GET("api/v1/rank/user/total")
    suspend fun getTotalTopRankUsers(
        @Query("commercialAreaCode") commercialAreaCode: String
    ): TotalTopRankUsersResponse

    @GET("open/v1/rank/top-users")
    suspend fun getTopRankUsers(): TopUsersResponse

    @GET("customer/rank")
    suspend fun getXpTypeRank(
        @Query("xpType") xpType: String,
        @Query("size") size: Int
    ): XpTypeRankResponse
}