package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedEventQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedNormalQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedRepeatQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuestApiService {
    @GET("customer/uncompletedTotalQuest")
    suspend fun getUncompletedTotalQuest(
        @Header("authorization") authorization: String,
        @Query("popularYn") popularYn: Boolean? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 8,
        @Query("sort") sort: List<String> = emptyList()
    ): UncompletedTotalQuestResponse

    @GET("customer/largeRewardQuest")
    suspend fun getLargeRewardQuest(
        @Header("authorization") authorization: String,
        @Query("rewardContent") rewardContent: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 3,
        @Query("sort") sort: List<String> = emptyList()
    ): LargeRewardQuestResponse

    // 미완료한 기본 퀘스트 목록 조회
    @GET("customer/uncompletedQuest")
    suspend fun getUncompletedNormalQuest(
        @Header("authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 60
    ): UncompletedNormalQuestResponse

    // 미완료한 반복 퀘스트 목록 조회
    @GET("customer/uncompletedRepeatQuest")
    suspend fun getUncompletedRepeatQuest(
        @Header("authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 60,
        @Query("status") status: String = "NONE"
    ): UncompletedRepeatQuestResponse

    //미완료한 이벤트 퀘스트 목록 조회
    @GET("customer/uncompletedEventQuest")
    suspend fun getUncompletedEventQuest(
        @Header("authorization") authorization: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 60
    ): UncompletedEventQuestResponse
}