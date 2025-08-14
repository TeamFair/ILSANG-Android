package com.ilsangtech.ilsang.feature.banner

import com.ilsangtech.ilsang.core.model.quest.BannerQuest

data class BannerDetailUiState(
    val id: String,
    val title: String,
    val description: String,
    val imageId: String,
    val bannerQuestUiState: BannerQuestUiState
)

sealed interface BannerQuestUiState {
    data object Loading : BannerQuestUiState
    data class Success(
        val onGoingQuests: List<BannerQuest>,
        val completedQuests: List<BannerQuest>
    ) : BannerQuestUiState

    data object Error : BannerQuestUiState
}
