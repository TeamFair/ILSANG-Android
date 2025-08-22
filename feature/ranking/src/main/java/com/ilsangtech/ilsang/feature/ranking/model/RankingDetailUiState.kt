package com.ilsangtech.ilsang.feature.ranking.model

sealed interface RankingDetailUiState {
    data object Loading : RankingDetailUiState
    data class Success(
        val currentSeason: SeasonUiModel.Season,
        val areaRankUiModel: AreaRankUiModel,
        val myRankUiModel: UserRankUiModel,
        val userRankList: List<UserRankUiModel>
    ) : RankingDetailUiState

    data object Error : RankingDetailUiState
}