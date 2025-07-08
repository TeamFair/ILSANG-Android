package com.ilsangtech.ilsang.core.data.quest.datasource

import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedEventQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedNormalQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedRepeatQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse

interface QuestDataSource {
    suspend fun getUncompletedTotalQuest(
        popularYn: Boolean? = null,
        page: Int = 0,
        size: Int = 8,
        sort: List<String> = emptyList()
    ): UncompletedTotalQuestResponse

    suspend fun getLargeRewardQuest(
        rewardContent: String,
        page: Int = 0,
        size: Int = 3,
        sort: List<String> = emptyList()
    ): LargeRewardQuestResponse

    suspend fun getUncompletedNormalQuest(
        page: Int,
        size: Int
    ): UncompletedNormalQuestResponse

    suspend fun getUncompletedRepeatQuest(
        page: Int,
        size: Int,
        status: String = "NONE"
    ): UncompletedRepeatQuestResponse

    suspend fun getUncompletedEventQuest(
        page: Int,
        size: Int
    ): UncompletedEventQuestResponse
}