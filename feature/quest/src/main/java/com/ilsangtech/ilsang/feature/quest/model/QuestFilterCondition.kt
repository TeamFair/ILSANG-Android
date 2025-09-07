package com.ilsangtech.ilsang.feature.quest.model

import com.ilsangtech.ilsang.core.model.NewQuestType

data class QuestFilterCondition(
    val areaCode: String,
    val questType: NewQuestType?,
    val orderExpiredDesc: Boolean?,
    val orderRewardDesc: Boolean?,
    val completedYn: Boolean
)
