package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestsResponse
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestDeletionRequest
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestRegistrationRequest
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.QuestDetailResponse
import com.ilsangtech.ilsang.core.network.model.quest.RecommendedQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.TypedQuestsResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedEventQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedNormalQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedRepeatQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import retrofit2.http.Body
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

    @GET("api/v1/quest/user/search/recommend")
    suspend fun getRecommendedQuest(
        @Query("commercialAreaCode") commercialAreaCode: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String>
    ): RecommendedQuestResponse

    @GET("api/v1/quest/user/search/type")
    suspend fun getTypedQuests(
        @Query("commercialAreaCode") commercialAreaCode: String,
        @Query("questType") type: String? = null,
        @Query("repeatFrequency") repeatFrequency: String? = null,
        @Query("orderRewardDesc") orderRewardDesc: Boolean? = null,
        @Query("favoriteYn") favoriteYn: Boolean? = null,
        @Query("completeYn") completeYn: Boolean = false,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = emptyList()
    ): TypedQuestsResponse

    @GET("api/v1/quest/user/search/reward")
    suspend fun getLargeRewardQuest(
        @Query("commercialAreaCode") commercialAreaCode: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3,
        @Query("sort") sort: List<String> = emptyList()
    ): LargeRewardQuestResponse

    @GET("api/v1/quest/search/banner/{bannerId}")
    suspend fun getBannerQuests(
        @Path("bannerId") bannerId: Int,
        @Query("orderExpiredDesc") orderExpiredDesc: Boolean?,
        @Query("orderRewardDesc") orderRewardDesc: Boolean?,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: List<String> = emptyList()
    ): BannerQuestsResponse

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

    @GET("api/v1/quest/user/{questId}")
    suspend fun getQuestDetail(
        @Path("questId") questId: Int
    ): QuestDetailResponse

    @POST("api/v1/quest/user/favorite")
    suspend fun registerFavoriteQuest(
        @Body favoriteQuestRegistrationRequest: FavoriteQuestRegistrationRequest
    )

    @DELETE("api/v1/quest/user/favorite")
    suspend fun deleteFavoriteQuest(
        @Body favoriteQuestDeletionRequest: FavoriteQuestDeletionRequest
    )
}