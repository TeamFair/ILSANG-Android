package com.ilsangtech.ilsang.feature.quest

import com.ilsangtech.ilsang.core.model.Quest

sealed interface QuestTabUiState {
    data object Loading : QuestTabUiState
    data class Success(val data: QuestTabUiData) : QuestTabUiState
    data class Error(val throwable: Throwable) : QuestTabUiState
}

data class QuestTabUiData(val questList: List<Quest>)

enum class SortType(val title: String) {
    FAVORITE("즐겨찾기만"),
    POINT_DESC("포인트 높은 순"),
    POINT_ASC("포인트 낮은 순"),
    POPULAR("인기순")
}