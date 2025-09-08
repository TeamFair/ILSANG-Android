package com.ilsangtech.ilsang.feature.quest.model

import com.ilsangtech.ilsang.core.model.quest.QuestType

data class QuestFilterCondition(
    val areaCode: String,
    val questType: QuestType?,
    val orderExpiredDesc: Boolean?,
    val orderRewardDesc: Boolean?,
    val completedYn: Boolean
)
