package com.ilsangtech.ilsang.feature.submit

import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.RewardPoint

data class SubmitQuestUiState(
    val questImageId: String,
    val title: String,
    val writerName: String,
    val questType: NewQuestType,
    val rewards: List<RewardPoint>
)
