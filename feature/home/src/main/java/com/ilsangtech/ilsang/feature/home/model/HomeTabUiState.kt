package com.ilsangtech.ilsang.feature.home.model

import com.ilsangtech.ilsang.core.model.UserRank
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest

sealed interface HomeTabUiState {
    data object Loading : HomeTabUiState
    data class Success(val data: HomeTabSuccessData) : HomeTabUiState
    data class Error(val throwable: Throwable) : HomeTabUiState
}

data class HomeTabSuccessData(
    val banners: List<Banner>,
    val popularQuests: List<PopularQuest>,
    val recommendedQuests: List<RecommendedQuest>,
    val largeRewardQuests: List<LargeRewardQuest>,
    val topRankUsers: List<UserRank>,
)
