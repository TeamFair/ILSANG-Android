package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import kotlinx.coroutines.flow.Flow

interface QuestRepository {
    fun getPopularQuests(commercialAreaCode: String): Flow<List<PopularQuest>>

    fun getRecommendedQuests(commercialAreaCode: String): Flow<List<RecommendedQuest>>

    fun getLargeRewardQuests(
        commercialAreaCode: String,
        isZoneCode: String?
    ): Flow<List<LargeRewardQuest>>

    fun getBannerQuests(
        bannerId: Int,
        completedYn: Boolean,
        orderExpiredDesc: Boolean? = null,
        orderRewardDesc: Boolean? = null,
        isZoneCode: String?
    ): Flow<PagingData<BannerQuest>>

    fun getTypedQuests(
        commercialAreaCode: String,
        questType: QuestType? = null,
        orderExpiredDesc: Boolean? = null,
        orderRewardDesc: Boolean? = null,
        favoriteYn: Boolean? = null,
        completedYn: Boolean = false
    ): Flow<PagingData<TypedQuest>>

    fun getQuestDetail(questId: Int, isIsZoneQuest: Boolean = false): Flow<QuestDetail>

    suspend fun registerFavoriteQuest(questId: Int): Result<Unit>

    suspend fun deleteFavoriteQuest(questId: Int): Result<Unit>
}