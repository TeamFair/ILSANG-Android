package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RankApiService {
    @GET("open/v1/rank/top-users")
    suspend fun getTopRankUsers(): TopUsersResponse

    @GET("customer/rank")
    suspend fun getXpTypeRank(
        @Header("authorization") authorization: String,
        @Query("xpType") xpType: String,
        @Query("size") size: Int
    ): XpTypeRankResponse
}