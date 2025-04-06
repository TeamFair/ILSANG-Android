package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.rank.TopUsersResponse
import retrofit2.http.GET

interface RankApiService {
    @GET("v1/rank/top-users")
    suspend fun getTopRankUsers(): TopUsersResponse
}