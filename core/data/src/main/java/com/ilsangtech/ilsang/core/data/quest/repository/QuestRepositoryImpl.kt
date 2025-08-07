package com.ilsangtech.ilsang.core.data.quest.repository

import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSource
import com.ilsangtech.ilsang.core.data.quest.toQuest
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.network.model.quest.QuestNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class QuestRepositoryImpl(
    private val questDataSource: QuestDataSource,
) : QuestRepository {
    private val questCache = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    override suspend fun getPopularQuests(): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedTotalQuest(
                popularYn = true,
                size = 8,
            ).data.map(QuestNetworkModel::toQuest)
        )
    }.combine(questCache) { quests, questCache ->
        quests.map { quest ->
            quest.copy(favoriteYn = questCache[quest.questId] ?: quest.favoriteYn)
        }
    }

    override suspend fun getRecommendedQuests(): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedTotalQuest(
                popularYn = false,
                size = 10
            ).data.map(QuestNetworkModel::toQuest)
        )
    }.combine(questCache) { quests, questCache ->
        quests.map { quest ->
            quest.copy(favoriteYn = questCache[quest.questId] ?: quest.favoriteYn)
        }
    }

    override suspend fun getLargeRewardQuests(): Flow<Map<String, List<Quest>>> = flow {
        val rewardContentList = listOf("INTELLECT", "SOCIABILITY", "STRENGTH", "FUN", "CHARM")
        emit(
            rewardContentList.associateWith { rewardContent ->
                questDataSource.getLargeRewardQuest(
                    rewardContent = rewardContent,
                    size = 3
                ).data.map(QuestNetworkModel::toQuest)
            }
        )
    }.combine(questCache) { quests, questCache ->
        quests.mapValues { (_, quests) ->
            quests.map { quest ->
                quest.copy(favoriteYn = questCache[quest.questId] ?: quest.favoriteYn)
            }
        }
    }

    override suspend fun getUncompletedNormalQuests(): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedNormalQuest(
                page = 0,
                size = 60
            ).data.map(QuestNetworkModel::toQuest)
        )
    }.combine(questCache) { quests, questCache ->
        quests.map { quest ->
            quest.copy(favoriteYn = questCache[quest.questId] ?: quest.favoriteYn)
        }
    }

    override suspend fun getUncompletedRepeatQuests(status: String): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedRepeatQuest(
                page = 0,
                size = 60,
                status = status
            ).data.map(QuestNetworkModel::toQuest)
        )
    }.combine(questCache) { quests, questCache ->
        quests.map { quest ->
            quest.copy(favoriteYn = questCache[quest.questId] ?: quest.favoriteYn)
        }
    }

    override suspend fun getUncompletedEventQuests(): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedEventQuest(
                page = 0,
                size = 60
            ).data.map(QuestNetworkModel::toQuest)
        )
    }.combine(questCache) { quests, questCache ->
        quests.map { quest ->
            quest.copy(favoriteYn = questCache[quest.questId] ?: quest.favoriteYn)
        }
    }

    override suspend fun registerFavoriteQuest(questId: String) {
        runCatching {
            questDataSource.registerFavoriteQuest(questId)
        }.onSuccess {
            questCache.update { it + (questId to true) }
        }
    }

    override suspend fun deleteFavoriteQuest(questId: String) {
        runCatching {
            questDataSource.deleteFavoriteQuest(questId)
        }.onSuccess {
            questCache.update { it + (questId to false) }
        }
    }
}