package com.ilsangtech.ilsang.feature.home

import com.ilsangtech.ilsang.core.model.Banner
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.UserRank

sealed interface HomeTabUiState {
    data object Loading : HomeTabUiState
    data class Success(val data: HomeTabSuccessData) : HomeTabUiState
    data class Error(val throwable: Throwable) : HomeTabUiState
}

data class HomeTabSuccessData(
    val banners: List<Banner>,
    val popularQuests: List<Quest>,
    val recommendedQuests: List<Quest>,
    val largeRewardQuests: Map<String, List<Quest>>,
    val topRankUsers: List<UserRank>,
)
