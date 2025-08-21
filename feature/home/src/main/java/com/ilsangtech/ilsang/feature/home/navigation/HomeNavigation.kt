package com.ilsangtech.ilsang.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.feature.home.HomeTabScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeBaseRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.homeNavigation(
    navigateToQuestTab: () -> Unit,
    navigateToRankingTab: () -> Unit,
    navigateToMyTab: () -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToSubmit: (String) -> Unit,
    onBannerClick: (Banner) -> Unit,
    onMyZoneClick: () -> Unit,
    onIsZoneClick: () -> Unit
) {
    navigation<HomeBaseRoute>(startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeTabScreen(
                navigateToQuestTab = navigateToQuestTab,
                navigateToMyTab = navigateToMyTab,
                navigateToRankingTab = navigateToRankingTab,
                navigateToSubmit = navigateToSubmit,
                navigateToProfile = navigateToProfile,
                onBannerClick = onBannerClick,
                onMyZoneClick = onMyZoneClick,
                onIsZoneClick = onIsZoneClick
            )
        }
    }
}