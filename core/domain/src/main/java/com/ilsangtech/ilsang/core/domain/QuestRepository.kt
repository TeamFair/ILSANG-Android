package com.ilsangtech.ilsang.core.domain

import androidx.paging.PagingData
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import kotlinx.coroutines.flow.Flow

interface QuestRepository {
    fun getPopularQuests(commercialAreaCode: String): Flow<List<PopularQuest>>

    fun getRecommendedQuests(commercialAreaCode: String): Flow<List<RecommendedQuest>>

    fun getLargeRewardQuests(commercialAreaCode: String): Flow<List<LargeRewardQuest>>

    fun getBannerQuests(
        bannerId: Int,
        completedYn: Boolean,
        orderExpiredDesc: Boolean? = null,
        orderRewardDesc: Boolean? = null
    ): Flow<PagingData<BannerQuest>>

    fun getTypedQuests(
        commercialAreaCode: String,
        questType: NewQuestType? = null,
        orderExpiredDesc: Boolean? = null,
        orderRewardDesc: Boolean? = null,
        favoriteYn: Boolean? = null,
        completeYn: Boolean = false
    ): Flow<PagingData<TypedQuest>>

    // 미완료한 기본 퀘스트 목록 조회
    suspend fun getUncompletedNormalQuests(): Flow<List<Quest>>

    // 미완료한 반복 퀘스트 목록 조회
    suspend fun getUncompletedRepeatQuests(status: String): Flow<List<Quest>>

    // 미완료한 이벤트 퀘스트 목록 조회
    suspend fun getUncompletedEventQuests(): Flow<List<Quest>>

    fun getQuestDetail(questId: Int): Flow<QuestDetail>

    suspend fun registerFavoriteQuest(questId: Int): Result<Unit>

    suspend fun deleteFavoriteQuest(questId: Int): Result<Unit>
}