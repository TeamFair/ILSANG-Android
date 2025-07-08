package com.ilsangtech.ilsang.core.data.quest.repository

import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSource
import com.ilsangtech.ilsang.core.data.quest.toQuest
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.network.model.quest.QuestNetworkModel

class QuestRepositoryImpl(
    private val questDataSource: QuestDataSource,
) : QuestRepository {
    override suspend fun getPopularQuests(): List<Quest> {
        return questDataSource.getUncompletedTotalQuest(
            popularYn = true,
            size = 8,
        ).data.map(QuestNetworkModel::toQuest)
    }

    override suspend fun getRecommendedQuests(): List<Quest> {
        return questDataSource.getUncompletedTotalQuest(
            popularYn = false,
            size = 10
        ).data.map(QuestNetworkModel::toQuest)
    }

    override suspend fun getLargeRewardQuests(): Map<String, List<Quest>> {
        val rewardContentList = listOf("INTELLECT", "SOCIABILITY", "STRENGTH", "FUN", "CHARM")
        return rewardContentList.associateWith { rewardContent ->
            questDataSource.getLargeRewardQuest(
                rewardContent = rewardContent,
                size = 3
            ).data.map(QuestNetworkModel::toQuest)
        }
    }

    override suspend fun getUncompletedNormalQuests(): List<Quest> {
        return questDataSource.getUncompletedNormalQuest(
            page = 0,
            size = 60
        ).data.map(QuestNetworkModel::toQuest)
    }

    override suspend fun getUncompletedRepeatQuests(status: String): List<Quest> {
        return questDataSource.getUncompletedRepeatQuest(
            page = 0,
            size = 60,
            status = status
        ).data.map(QuestNetworkModel::toQuest)
    }

    override suspend fun getUncompletedEventQuests(): List<Quest> {
        return questDataSource.getUncompletedEventQuest(
            page = 0,
            size = 60
        ).data.map(QuestNetworkModel::toQuest)
    }

}