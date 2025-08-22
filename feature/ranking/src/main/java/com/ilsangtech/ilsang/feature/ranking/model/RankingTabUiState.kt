package com.ilsangtech.ilsang.feature.ranking.model

sealed interface RankingTabUiState {
    data object Loading : RankingTabUiState
    data class Success(
        val metroRankAreas: List<AreaRankUiModel>,
        val commercialRankAreas: List<AreaRankUiModel>,
        val contributionRankUsers: List<UserRankUiModel>
    ) : RankingTabUiState

    data object Error : RankingTabUiState
}