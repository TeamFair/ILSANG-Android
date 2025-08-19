package com.ilsangtech.ilsang.core.data.quest.repository

import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSource
import com.ilsangtech.ilsang.core.data.quest.mapper.toLargeRewardQuest
import com.ilsangtech.ilsang.core.data.quest.mapper.toPopularQuest
import com.ilsangtech.ilsang.core.data.quest.toQuest
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestNetworkModel
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

    override suspend fun getPopularQuests(commercialAreaCode: String): Flow<List<PopularQuest>> =
        flow {
            emit(
                questDataSource.getPopularQuest(commercialAreaCode = commercialAreaCode)
                    .content.map(PopularQuestNetworkModel::toPopularQuest)
            )
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

    override suspend fun getLargeRewardQuests(commercialAreaCode: String): Flow<List<LargeRewardQuest>> =
        flow {
            val largeRewardQuests =
                questDataSource.getLargeRewardQuest(
                    commercialAreaCode = commercialAreaCode,
                    size = 3
                ).content.map(LargeRewardQuestNetworkModel::toLargeRewardQuest)
            emit(largeRewardQuests)
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