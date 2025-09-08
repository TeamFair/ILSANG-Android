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
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.PopularQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.RecommendedQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.TypedQuestNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class QuestRepositoryImpl(
    private val questDataSource: QuestDataSource,
) : QuestRepository {
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
        completedYn: Boolean,
        orderExpiredDesc: Boolean?,
        orderRewardDesc: Boolean?
    ): Flow<PagingData<BannerQuest>> {
        return questDataSource.getBannerQuests(
            bannerId = bannerId,
            completedYn = completedYn,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc
        ).map { it.map(BannerQuestNetworkModel::toBannerQuest) }
    }

    override fun getTypedQuests(
        commercialAreaCode: String,
        questType: QuestType?,
        orderExpiredDesc: Boolean?,
        orderRewardDesc: Boolean?,
        favoriteYn: Boolean?,
        completedYn: Boolean
    ): Flow<PagingData<TypedQuest>> {
        val (type, repeatFrequency) = when (questType) {
            is QuestType.Normal -> "NORMAL" to null
            is QuestType.Event -> "EVENT" to null
            is QuestType.Repeat -> "REPEAT" to when (questType) {
                is QuestType.Repeat.Daily -> "DAILY"
                is QuestType.Repeat.Weekly -> "WEEKLY"
                is QuestType.Repeat.Monthly -> "MONTHLY"
            }

            else -> null to null
        }
        return questDataSource.getTypedQuests(
            commercialAreaCode = commercialAreaCode,
            type = type,
            repeatFrequency = repeatFrequency,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc,
            favoriteYn = favoriteYn,
            completeYn = completedYn
        ).map { it.map(TypedQuestNetworkModel::toTypedQuest) }
    }

    override fun getQuestDetail(questId: Int): Flow<QuestDetail> = flow {
        emit(
            questDataSource.getQuestDetail(questId).toQuestDetail()
        )
    }

    override suspend fun registerFavoriteQuest(questId: Int): Result<Unit> {
        return runCatching {
            questDataSource.registerFavoriteQuest(questId)
        }
    }

    override suspend fun deleteFavoriteQuest(questId: Int): Result<Unit> {
        return runCatching {
            questDataSource.deleteFavoriteQuest(questId)
        }
    }
}