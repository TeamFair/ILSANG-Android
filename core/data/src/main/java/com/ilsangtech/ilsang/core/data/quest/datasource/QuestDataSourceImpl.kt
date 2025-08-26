package com.ilsangtech.ilsang.core.data.quest.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.data.quest.BannerQuestPagingSource
import com.ilsangtech.ilsang.core.data.quest.TypedQuestPagingSource
import com.ilsangtech.ilsang.core.network.api.QuestApiService
import com.ilsangtech.ilsang.core.network.model.quest.BannerQuestNetworkModel
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestDeletionRequest
import com.ilsangtech.ilsang.core.network.model.quest.FavoriteQuestRegistrationRequest
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

    override suspend fun getRecommendedQuest(
        commercialAreaCode: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): RecommendedQuestResponse {
        return questApiService.getRecommendedQuest(
            commercialAreaCode = commercialAreaCode,
            page = page,
            size = size,
            sort = sort
        )
    }

    override suspend fun getPopularQuest(
        commercialAreaCode: String,
        page: Int,
        size: Int,
        sort: List<String>
    ): PopularQuestResponse {
        return questApiService.getPopularQuest(
            commercialAreaCode = commercialAreaCode,
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

    override fun getBannerQuests(
        bannerId: Int,
        completedYn: Boolean,
        orderExpiredDesc: Boolean?,
        orderRewardDesc: Boolean?
    ): Flow<PagingData<BannerQuestNetworkModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                BannerQuestPagingSource(
                    questApiService = questApiService,
                    bannerId = bannerId,
                    completedYn = completedYn,
                    orderExpiredDesc = orderExpiredDesc,
                    orderRewardDesc = orderRewardDesc
                )
            }
        ).flow
    }

    override fun getTypedQuests(
        commercialAreaCode: String,
        type: String?,
        repeatFrequency: String?,
        orderExpiredDesc: Boolean?,
        orderRewardDesc: Boolean?,
        favoriteYn: Boolean?,
        completeYn: Boolean
    ): Flow<PagingData<TypedQuestNetworkModel>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                TypedQuestPagingSource(
                    questApiService = questApiService,
                    commercialAreaCode = commercialAreaCode,
                    type = type,
                    repeatFrequency = repeatFrequency,
                    orderExpiredDesc = orderExpiredDesc,
                    orderRewardDesc = orderRewardDesc,
                    favoriteYn = favoriteYn,
                    completeYn = completeYn
                )
            }
        ).flow
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

    override suspend fun getQuestDetail(questId: Int): QuestDetailResponse {
        return questApiService.getQuestDetail(questId)
    }

    override suspend fun registerFavoriteQuest(questId: Int) {
        return questApiService.registerFavoriteQuest(
            FavoriteQuestRegistrationRequest(questId)
        )
    }

    override suspend fun deleteFavoriteQuest(questId: Int) {
        return questApiService.deleteFavoriteQuest(
            FavoriteQuestDeletionRequest(questId)
        )
    }
}