package com.ilsangtech.ilsang.feature.ranking.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.ranking.RankingDetailScreen
import com.ilsangtech.ilsang.feature.ranking.RankingTabScreen
import com.ilsangtech.ilsang.feature.ranking.SeasonRewardScreen
import kotlinx.serialization.Serializable

@Serializable
data object RankingBaseRoute

@Serializable
data object RankingRoute

@Serializable
data class RankingDetailRoute(
    val seasonId: Int?,
    val isMetro: Boolean,
    val areaCode: String,
    val areaName: String,
    val rank: Int,
    val point: Int,
    val images: List<String>
)

@Serializable
data object SeasonRewardRoute

fun NavGraphBuilder.rankingNavigation(
    navigateToRankingDetail: (RankingDetailRoute) -> Unit,
    navigateToUserProfile: (String) -> Unit,
    navigateToQuestTab: () -> Unit,
    navigateToSeasonReward: () -> Unit,
    onBackButtonClick: () -> Unit
) {
    navigation<RankingBaseRoute>(startDestination = RankingRoute) {
        composable<RankingRoute> {
            RankingTabScreen(
                navigateToRankingDetail = navigateToRankingDetail,
                navigateToUserProfile = navigateToUserProfile,
                navigateToQuestTab = navigateToQuestTab,
                navigateToSeasonReward = navigateToSeasonReward
            )
        }

        composable<RankingDetailRoute> {
            RankingDetailScreen(
                onBackButtonClick = onBackButtonClick,
                navigateToUserProfile = navigateToUserProfile
            )
        }

        composable<SeasonRewardRoute> {
            SeasonRewardScreen(onBackButtonClick = onBackButtonClick)
        }
    }
}