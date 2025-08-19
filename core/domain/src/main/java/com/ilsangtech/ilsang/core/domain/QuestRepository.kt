package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import kotlinx.coroutines.flow.Flow

interface QuestRepository {
    suspend fun getPopularQuests(commercialAreaCode: String): Flow<List<PopularQuest>>

    suspend fun getRecommendedQuests(): Flow<List<Quest>>

    suspend fun getLargeRewardQuests(commercialAreaCode: String): Flow<List<LargeRewardQuest>>

    // 미완료한 기본 퀘스트 목록 조회
    suspend fun getUncompletedNormalQuests(): Flow<List<Quest>>

    // 미완료한 반복 퀘스트 목록 조회
    suspend fun getUncompletedRepeatQuests(status: String): Flow<List<Quest>>

    // 미완료한 이벤트 퀘스트 목록 조회
    suspend fun getUncompletedEventQuests(): Flow<List<Quest>>

    suspend fun registerFavoriteQuest(questId: String)

    suspend fun deleteFavoriteQuest(questId: String)
}