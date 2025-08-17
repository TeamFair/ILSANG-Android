package com.ilsangtech.ilsang.feature.submit

import com.ilsangtech.ilsang.core.model.NewQuestType

data class SubmitQuestUiState(
    val questImageId: String,
    val title: String,
    val locationName: String,
    val questType: NewQuestType,
    val point: Int
)
