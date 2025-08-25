package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.mission.MissionDetailResponse
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryResponse
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MissionApiService {
    @GET("api/v1/mission/{id}")
    suspend fun getMissionById(
        @Path("id") id: Int
    ): MissionDetailResponse

    @GET("api/v1/mission/user/history")
    suspend fun getUserMissionHistory(
        @Query("userId") userId: String?,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = emptyList()
    ): UserMissionHistoryResponse

    @GET("api/v1/mission/user/history/random")
    suspend fun getRandomMissionHistory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = emptyList()
    ): RandomMissionHistoryResponse
}