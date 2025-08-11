package com.ilsangtech.ilsang.feature.ranking.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.ranking.RankingTabScreen
import kotlinx.serialization.Serializable

@Serializable
data object RankingBaseRoute

@Serializable
data object RankingRoute

fun NavGraphBuilder.rankingNavigation(
    navigateToProfile: (String) -> Unit
) {
    navigation<RankingBaseRoute>(
        startDestination = RankingRoute
    ) {
        composable<RankingRoute> {
            RankingTabScreen(navigateToProfile = navigateToProfile)
        }
    }
}