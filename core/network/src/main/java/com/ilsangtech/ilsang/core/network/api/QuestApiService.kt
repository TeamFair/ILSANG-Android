package com.ilsangtech.ilsang.core.network.api

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
}