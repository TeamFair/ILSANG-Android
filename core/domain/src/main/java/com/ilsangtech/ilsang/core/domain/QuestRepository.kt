package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.Quest

interface QuestRepository {
    suspend fun getPopularQuests(): List<Quest>

    suspend fun getRecommendedQuests(): List<Quest>

    suspend fun getLargeRewardQuests(): List<Map<String, List<Quest>>>
}