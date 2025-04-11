package com.ilsangtech.ilsang.core.data.quest.datasource

import com.ilsangtech.ilsang.core.network.api.QuestApiService
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
        authorization: String,
        popularYn: Boolean?,
        page: Int,
        size: Int,
        sort: List<String>
    ): UncompletedTotalQuestResponse {
        return questApiService.getUncompletedTotalQuest(
            authorization = authorization,
            page = page,
            size = size,
            sort = sort
        )
    }

    override suspend fun getLargeRewardQuest(
        authorization: String,
        rewardContent: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): LargeRewardQuestResponse {
        return questApiService.getLargeRewardQuest(
            authorization = authorization,
            rewardContent = rewardContent,
            page = page,
            size = size,
            sort = sort
        )
    }

    override suspend fun getUncompletedNormalQuest(
        authorization: String,
        page: Int,
        size: Int
    ): UncompletedNormalQuestResponse {
        return questApiService.getUncompletedNormalQuest(
            authorization = authorization,
            page = page,
            size = size
        )
    }

    override suspend fun getUncompletedRepeatQuest(
        authorization: String,
        page: Int,
        size: Int,
        status: String
    ): UncompletedRepeatQuestResponse {
        return questApiService.getUncompletedRepeatQuest(
            authorization = authorization,
            page = page,
            size = size,
            status = status
        )
    }

    override suspend fun getUncompletedEventQuest(
        authorization: String,
        page: Int,
        size: Int
    ): UncompletedEventQuestResponse {
        return questApiService.getUncompletedEventQuest(
            authorization = authorization,
            page = page,
            size = size
        )
    }
}