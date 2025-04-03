package com.ilsangtech.ilsang.core.network.api

import com.ilsangtech.ilsang.core.network.model.quest.PageableRequest
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuestApiService {
    @GET("customer/quest/uncompletedTotalQuest")
    suspend fun getUncompletedTotalQuest(
        @Header("Authorization") authorization: String,
        @Query("popularYn") popularYn: Boolean? = null,
        @Query("pageable") pageable: PageableRequest
    ): UncompletedTotalQuestResponse
}