package com.ilsangtech.ilsang.core.data.quest.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.ilsangtech.ilsang.core.data.quest.datasource.QuestDataSource
import com.ilsangtech.ilsang.core.data.quest.mapper.toBannerQuest
import com.ilsangtech.ilsang.core.data.quest.mapper.toLargeRewardQuest
import com.ilsangtech.ilsang.core.data.quest.mapper.toPopularQuest
import com.ilsangtech.ilsang.core.data.quest.mapper.toQuestDetail
import com.ilsangtech.ilsang.core.data.quest.mapper.toRecommendedQuest
import com.ilsangtech.ilsang.core.data.quest.mapper.toTypedQuest
import com.ilsangtech.ilsang.core.data.quest.toQuest
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.QuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RecommendedQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.TypedQuestNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class QuestRepositoryImpl(
    private val questDataSource: QuestDataSource,
) : QuestRepository {
    private val questCache = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    override fun getPopularQuests(commercialAreaCode: String): Flow<List<PopularQuest>> =
        flow {
            emit(
                questDataSource.getPopularQuest(commercialAreaCode = commercialAreaCode)
                    .content.map(PopularQuestNetworkModel::toPopularQuest)
            )
        }

    override fun getRecommendedQuests(commercialAreaCode: String): Flow<List<RecommendedQuest>> =
        flow {
            emit(
                questDataSource.getRecommendedQuest(
                    commercialAreaCode = commercialAreaCode
                ).content.map(RecommendedQuestNetworkModel::toRecommendedQuest)
            )
        }

    override fun getLargeRewardQuests(commercialAreaCode: String): Flow<List<LargeRewardQuest>> =
        flow {
            val largeRewardQuests =
                questDataSource.getLargeRewardQuest(
                    commercialAreaCode = commercialAreaCode,
                    size = 3
                ).content.map(LargeRewardQuestNetworkModel::toLargeRewardQuest)
            emit(largeRewardQuests)
        }

    override fun getBannerQuests(
        bannerId: Int,
        orderExpiredDesc: Boolean?,
        orderRewardDesc: Boolean?
    ): Flow<PagingData<BannerQuest>> {
        return questDataSource.getBannerQuests(
            bannerId = bannerId,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc
        ).map { it.map(BannerQuestNetworkModel::toBannerQuest) }
    }

    override fun getTypedQuests(
        commercialAreaCode: String,
        questType: NewQuestType?,
        orderRewardDesc: Boolean?,
        favoriteYn: Boolean?,
        completeYn: Boolean
    ): Flow<PagingData<TypedQuest>> {
        val (type, repeatFrequency) = when (questType) {
            is NewQuestType.Normal -> "Normal" to null
            is NewQuestType.Event -> "Event" to null
            is NewQuestType.Repeat -> "Repeat" to when (questType) {
                is NewQuestType.Repeat.Daily -> "DAILY"
                is NewQuestType.Repeat.Weekly -> "WEEKLY"
                is NewQuestType.Repeat.Monthly -> "MONTHLY"
            }

            else -> null to null
        }
        return questDataSource.getTypedQuests(
            commercialAreaCode = commercialAreaCode,
            type = type,
            repeatFrequency = repeatFrequency,
            orderRewardDesc = orderRewardDesc,
            favoriteYn = favoriteYn,
            completeYn = completeYn
        ).map { it.map(TypedQuestNetworkModel::toTypedQuest) }
    }

    override suspend fun getUncompletedNormalQuests(): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedNormalQuest(
                page = 0,
                size = 60
            ).data.map(QuestNetworkModel::toQuest)
        )
    }

    override suspend fun getUncompletedRepeatQuests(status: String): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedRepeatQuest(
                page = 0,
                size = 60,
                status = status
            ).data.map(QuestNetworkModel::toQuest)
        )
    }

    override suspend fun getUncompletedEventQuests(): Flow<List<Quest>> = flow {
        emit(
            questDataSource.getUncompletedEventQuest(
                page = 0,
                size = 60
            ).data.map(QuestNetworkModel::toQuest)
        )
    }

    override fun getQuestDetail(questId: Int): Flow<QuestDetail> = flow {
        emit(
            questDataSource.getQuestDetail(questId).toQuestDetail()
        )
    }.combine(questCache) { quest, questCache ->
        quest.copy(favoriteYn = questCache[quest.id] ?: quest.favoriteYn)
    }

    override suspend fun registerFavoriteQuest(questId: Int) {
        runCatching {
            questDataSource.registerFavoriteQuest(questId)
        }.onSuccess {
            questCache.update { it + (questId to true) }
        }
    }

    override suspend fun deleteFavoriteQuest(questId: Int) {
        runCatching {
            questDataSource.deleteFavoriteQuest(questId)
        }.onSuccess {
            questCache.update { it + (questId to false) }
        }
    }
}