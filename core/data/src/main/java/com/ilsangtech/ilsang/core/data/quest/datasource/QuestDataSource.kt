package com.ilsangtech.ilsang.core.data.quest.datasource

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.QuestDetailResponse
import com.ilsangtech.ilsang.core.network.model.quest.RecommendedQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.TypedQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedEventQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedNormalQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedRepeatQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import kotlinx.coroutines.flow.Flow

interface QuestDataSource {
    suspend fun getUncompletedTotalQuest(
        popularYn: Boolean? = null,
        page: Int = 0,
        size: Int = 8,
        sort: List<String> = emptyList()
    ): UncompletedTotalQuestResponse

    suspend fun getPopularQuest(
        commercialAreaCode: String,
        page: Int = 0,
        size: Int = 8,
        sort: List<String> = emptyList()
    ): PopularQuestResponse

    suspend fun getRecommendedQuest(
        commercialAreaCode: String,
        page: Int = 0,
        size: Int = 10,
        sort: List<String> = emptyList()
    ): RecommendedQuestResponse

    suspend fun getLargeRewardQuest(
        commercialAreaCode: String,
        page: Int = 0,
        size: Int = 3,
        sort: List<String> = emptyList()
    ): LargeRewardQuestResponse

    fun getTypedQuests(
        commercialAreaCode: String,
        type: String? = null,
        repeatFrequency: String? = null,
        orderRewardDesc: Boolean? = null,
        favoriteYn: Boolean? = null,
        completeYn: Boolean = false
    ): Flow<PagingData<TypedQuestNetworkModel>>

    suspend fun getUncompletedNormalQuest(
        page: Int,
        size: Int
    ): UncompletedNormalQuestResponse

    suspend fun getUncompletedRepeatQuest(
        page: Int,
        size: Int,
        status: String = "NONE"
    ): UncompletedRepeatQuestResponse

    suspend fun getUncompletedEventQuest(
        page: Int,
        size: Int
    ): UncompletedEventQuestResponse

    suspend fun getQuestDetail(questId: Int): QuestDetailResponse

    suspend fun registerFavoriteQuest(questId: Int)

    suspend fun deleteFavoriteQuest(questId: Int)
}