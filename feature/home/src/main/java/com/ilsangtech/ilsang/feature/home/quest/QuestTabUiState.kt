package com.ilsangtech.ilsang.feature.home.quest

import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType

sealed interface QuestTabUiState {
    data object Loading : QuestTabUiState
    data class Success(val data: QuestTabUiData) : QuestTabUiState
    data class Error(val throwable: Throwable) : QuestTabUiState
}

data class QuestTabUiData(
    val selectedQuestType: QuestType,
    val selectedSortType: List<String>,
    val questList: List<Quest>,
)

