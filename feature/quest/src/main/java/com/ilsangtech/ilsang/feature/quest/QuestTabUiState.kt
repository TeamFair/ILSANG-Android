package com.ilsangtech.ilsang.feature.quest

import com.ilsangtech.ilsang.core.model.Quest

sealed interface QuestTabUiState {
    data object Loading : QuestTabUiState
    data class Success(val data: QuestTabUiData) : QuestTabUiState
    data class Error(val throwable: Throwable) : QuestTabUiState
}

data class QuestTabUiData(val questList: List<Quest>)

