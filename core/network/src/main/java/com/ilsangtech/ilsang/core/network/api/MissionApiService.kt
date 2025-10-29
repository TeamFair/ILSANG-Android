package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.mission.ExampleMissionHistoryResponse
import com.ilsangtech.ilsang.core.network.model.mission.MissionDetailResponse
import com.ilsangtech.ilsang.core.network.model.mission.MissionHistoryEmojiRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.mission.MissionSubmitRequest
import com.ilsangtech.ilsang.core.network.model.mission.MissionSubmitResponse
import com.ilsangtech.ilsang.core.network.model.mission.RandomMissionHistoryResponse
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryDetailNetworkModel
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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
        @Query("missionType") missionType: String,
        @Query("sort") sort: List<String> = emptyList()
    ): UserMissionHistoryResponse

    @GET("api/v1/mission/user/history/detail")
    suspend fun getUserMissionHistoryDetail(
        @Query("missionHistoryId") missionHistoryId: Int
    ): UserMissionHistoryDetailNetworkModel

    @GET("api/v1/mission/user/history/random")
    suspend fun getRandomMissionHistory(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = emptyList()
    ): RandomMissionHistoryResponse

    @GET("api/v1/mission/user/history/example")
    suspend fun getExampleMissionHistory(
        @Query("missionId") missionId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = emptyList()
    ): ExampleMissionHistoryResponse

    @POST("api/v1/challenge/mission")
    suspend fun submitMission(@Body request: MissionSubmitRequest): MissionSubmitResponse

    @POST("api/v1/mission/user/history/{missionHistoryId}/view-count")
    suspend fun updateMissionHistoryViewCount(
        @Path("missionHistoryId") missionHistoryId: Int
    )

    @POST("api/v1/mission/user/history/{missionHistoryId}/emoji")
    suspend fun registerMissionHistoryEmoji(
        @Path("missionHistoryId") missionHistoryId: Int,
        @Body request: MissionHistoryEmojiRegistrationRequest
    )

    @PUT("api/v1/mission/user/history/{missionHistoryId}")
    suspend fun reportMissionHistory(
        @Path("missionHistoryId") missionHistoryId: Int
    )

    @DELETE("api/v1/mission/user/history/{missionHistoryId}")
    suspend fun deleteMissionHistory(
        @Path("missionHistoryId") missionHistoryId: Int
    )

    @DELETE("api/v1/mission/user/history/{missionHistoryId}/emoji")
    suspend fun deleteMissionHistoryEmoji(
        @Path("missionHistoryId") missionHistoryId: Int,
        @Query("emojiType") emojiType: String
    )
}