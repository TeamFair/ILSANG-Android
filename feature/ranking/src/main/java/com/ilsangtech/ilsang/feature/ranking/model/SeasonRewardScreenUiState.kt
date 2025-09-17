package com.ilsangtech.ilsang.feature.ranking.model

data class SeasonRewardScreenUiState(
    val selectedType: RewardUiModel = RewardUiModel.Metro,
    val selectedRewardTitleState: SeasonRewardTitleUiState = SeasonRewardTitleUiState.Loading
)

sealed interface SeasonRewardTitleUiState {
    data object Loading : SeasonRewardTitleUiState
    data class Success(
        val seasonRewardTitleList: List<SeasonRewardTitleUiModel>
    ) : SeasonRewardTitleUiState
}