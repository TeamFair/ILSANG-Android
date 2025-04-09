package com.ilsangtech.ilsang.core.data.quest.datasource

import com.ilsangtech.ilsang.core.network.model.quest.LargeRewardQuestResponse
import com.ilsangtech.ilsang.core.network.model.quest.UncompletedTotalQuestResponse

interface QuestDataSource {
    suspend fun getUncompletedTotalQuest(
        authorization: String,
        popularYn: Boolean? = null,
        page: Int = 0,
        size: Int = 8,
        sort: List<String> = emptyList()
    ): UncompletedTotalQuestResponse

    suspend fun getLargeRewardQuest(
        authorization: String,
        rewardContent: String,
        page: Int = 0,
        size: Int = 3,
        sort: List<String> = emptyList()
    ): LargeRewardQuestResponse
}