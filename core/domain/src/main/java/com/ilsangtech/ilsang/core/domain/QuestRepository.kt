package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.Quest

interface QuestRepository {
    suspend fun getPopularQuests(): List<Quest>

    suspend fun getRecommendedQuests(): List<Quest>

    suspend fun getLargeRewardQuests(): Map<String, List<Quest>>

    // 미완료한 기본 퀘스트 목록 조회
    suspend fun getUncompletedNormalQuests(): List<Quest>

    // 미완료한 반복 퀘스트 목록 조회
    suspend fun getUncompletedRepeatQuests(status: String): List<Quest>

    // 미완료한 이벤트 퀘스트 목록 조회
    suspend fun getUncompletedEventQuests(): List<Quest>

}