package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestDeletionResponse
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedEventQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedNormalQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedRepeatQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface QuestApiService {
    @GET("customer/uncompletedTotalQuest")
    suspend fun getUncompletedTotalQuest(
        @Query("popularYn") popularYn: Boolean? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 8,
        @Query("sort") sort: List<String> = emptyList()
    ): UncompletedTotalQuestResponse

    @GET("api/v1/quest/user/search/popular")
    suspend fun getPopularQuest(
        @Query("commercialAreaCode") commercialAreaCode: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ): PopularQuestResponse

    @GET("api/v1/quest/user/search/reward")
    suspend fun getLargeRewardQuest(
        @Query("commercialAreaCode") commercialAreaCode: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3,
        @Query("sort") sort: List<String> = emptyList()
    ): LargeRewardQuestResponse

    // 미완료한 기본 퀘스트 목록 조회
    @GET("customer/uncompletedQuest")
    suspend fun getUncompletedNormalQuest(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 60
    ): UncompletedNormalQuestResponse

    // 미완료한 반복 퀘스트 목록 조회
    @GET("customer/uncompletedRepeatQuest")
    suspend fun getUncompletedRepeatQuest(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 60,
        @Query("status") status: String = "NONE"
    ): UncompletedRepeatQuestResponse

    //미완료한 이벤트 퀘스트 목록 조회
    @GET("customer/uncompletedEventQuest")
    suspend fun getUncompletedEventQuest(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 60
    ): UncompletedEventQuestResponse

    @POST("customer/quest/{questId}/favorite")
    suspend fun registerFavoriteQuest(
        @Path("questId") questId: String
    ): FavoriteQuestRegistrationResponse

    @DELETE("customer/quest/{questId}/favorite")
    suspend fun deleteFavoriteQuest(
        @Path("questId") questId: String
    ): FavoriteQuestDeletionResponse
}