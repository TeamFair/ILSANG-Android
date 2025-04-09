package com.ilsangtech.ilsang.core.data.quest.repository

import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSource
import com.ilsangtech.ilsang.core.data.quest.toQuest
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.network.model.quest.QuestNetworkModel

class QuestRepositoryImpl(
    private val questDataSource: QuestDataSource,
    private val userRepository: UserRepository
) : QuestRepository {
    override suspend fun getPopularQuests(): List<Quest> {
        return questDataSource.getUncompletedTotalQuest(
            authorization = userRepository.currentUser!!.authorization!!,
            popularYn = true,
            size = 8,
        ).data.map(QuestNetworkModel::toQuest)
    }

    override suspend fun getRecommendedQuests(): List<Quest> {
        return questDataSource.getUncompletedTotalQuest(
            authorization = userRepository.currentUser!!.authorization!!,
            popularYn = false,
            size = 10
        ).data.map(QuestNetworkModel::toQuest)
    }

    override suspend fun getLargeRewardQuests(): Map<String, List<Quest>> {
        val rewardContentList = listOf("INTELLECT", "SOCIABILITY", "STRENGTH", "FUN", "CHARM")
        return rewardContentList.associateWith { rewardContent ->
            questDataSource.getLargeRewardQuest(
                authorization = userRepository.currentUser!!.authorization!!,
                rewardContent = rewardContent,
                size = 3
            ).data.map(QuestNetworkModel::toQuest)
        }
    }

}