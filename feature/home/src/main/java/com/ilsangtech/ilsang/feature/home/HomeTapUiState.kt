package com.ilsangtech.ilsang.feature.home

import com.ilsangtech.ilsang.core.model.Banner
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.UserRank

sealed interface HomeTapUiState {
    data object Loading : HomeTapUiState
    data class Success(val data: HomeTapSuccessData) : HomeTapUiState
    data class Error(val throwable: Throwable) : HomeTapUiState
}

data class HomeTapSuccessData(
    val banners: List<Banner>,
    val popularQuests: List<Quest>,
    val recommendedQuests: List<Quest>,
    val largeRewardQuests: Map<String, List<Quest>>,
    val topRankUsers: List<UserRank>,
)
