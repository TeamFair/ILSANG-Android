package com.ilsangtech.ilsang.feature.quest_detail.model

sealed interface QuestDetailUiState {
    data object Loading : QuestDetailUiState
    data class Success(val uiModel: QuestDetailUiModel) : QuestDetailUiState
    data object Error : QuestDetailUiState
}