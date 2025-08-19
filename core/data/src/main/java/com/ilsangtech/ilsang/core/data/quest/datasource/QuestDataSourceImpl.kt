package com.ilsangtech.ilsang.core.data.quest.datasource

import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestDeletionResponse
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestRegistrationResponse
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedEventQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedNormalQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedRepeatQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse
import javax.inject.Inject

class QuestDataSourceImpl @Inject constructor(
    private val questApiService: QuestApiService
) : QuestDataSource {
    override suspend fun getUncompletedTotalQuest(
        popularYn: Boolean?,
        page: Int,
        size: Int,
        sort: List<String>
    ): UncompletedTotalQuestResponse {
        return questApiService.getUncompletedTotalQuest(
            page = page,
            size = size,
            sort = sort
        )
    }

    override suspend fun getLargeRewardQuest(
        commercialAreaCode: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): LargeRewardQuestResponse {
        return questApiService.getLargeRewardQuest(
            commercialAreaCode = commercialAreaCode,
            page = page,
            size = size,
            sort = sort
        )
    }

    override suspend fun getUncompletedNormalQuest(
        page: Int,
        size: Int
    ): UncompletedNormalQuestResponse {
        return questApiService.getUncompletedNormalQuest(
            page = page,
            size = size
        )
    }

    override suspend fun getUncompletedRepeatQuest(
        page: Int,
        size: Int,
        status: String
    ): UncompletedRepeatQuestResponse {
        return questApiService.getUncompletedRepeatQuest(
            page = page,
            size = size,
            status = status
        )
    }

    override suspend fun getUncompletedEventQuest(
        page: Int,
        size: Int
    ): UncompletedEventQuestResponse {
        return questApiService.getUncompletedEventQuest(
            page = page,
            size = size
        )
    }

    override suspend fun registerFavoriteQuest(
        questId: String
    ): FavoriteQuestRegistrationResponse {
        return questApiService.registerFavoriteQuest(questId)
    }

    override suspend fun deleteFavoriteQuest(
        questId: String
    ): FavoriteQuestDeletionResponse {
        return questApiService.deleteFavoriteQuest(questId)
    }
}