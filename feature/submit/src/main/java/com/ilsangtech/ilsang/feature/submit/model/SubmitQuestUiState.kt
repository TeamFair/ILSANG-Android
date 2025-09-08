package com.ilsangtech.ilsang.feature.submit.model

import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RewardPoint

data class SubmitQuestUiState(
    val questImageId: String?,
    val title: String,
    val writerName: String,
    val questType: QuestType,
    val rewards: List<RewardPoint>
)
