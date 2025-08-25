package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.mission.MissionDetailResponse
import com.ilsangtech.ilsang.core.network.model.mission.MissionHistoryEmojiUpdateRequest
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryResponse
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("api/v1/mission/user/history/{missionHistoryId}/view-count")
    suspend fun updateMissionHistoryViewCount(
        @Path("missionHistoryId") missionHistoryId: Int
    )

    @POST("api/v1/mission/user/history/{missionHistoryId}/emoji")
    suspend fun updateMissionHistoryEmoji(
        @Path("missionHistoryId") missionHistoryId: Int,
        @Body request: MissionHistoryEmojiUpdateRequest
    )

    @PUT("api/v1/mission/user/history/{missionHistoryId}")
    suspend fun reportMissionHistory(
        @Path("missionHistoryId") missionHistoryId: Int
    )
}