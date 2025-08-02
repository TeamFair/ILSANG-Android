package com.ilsangtech.ilsang.feature.home

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.home.approval.ApprovalScreen
import com.ilsangtech.ilsang.feature.home.home.HomeTapScreen
import com.ilsangtech.ilsang.feature.home.ranking.RankingScreen

fun NavGraphBuilder.homeNavigation(
    navController: NavHostController,
    navigateToProfile: (String) -> Unit
) {
    navigation(
        route = "home",
        startDestination = HomeTap.Home.name
    ) {
        composable(HomeTap.Home.name) { backStackEntry ->
            val homeViewModel = hiltViewModel<HomeViewModel>(backStackEntry)
            val userInfo by homeViewModel.myInfo.collectAsStateWithLifecycle()
            HomeTapScreen(
                userNickname = userInfo?.nickname,
                homeViewModel = homeViewModel,
                onApproveButtonClick = {
                    homeViewModel.selectQuest(it)
                },
                navigateToQuestTab = {
                    homeViewModel.selectSortType("포인트 높은 순")
                    navController.navigate(HomeTap.Quest.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToMyTab = {
                    navController.navigate(HomeTap.My.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToSubmit = {
                    navController.navigate("Submit")
                },
                navigateToRankingTab = {
                    navController.navigate(HomeTap.Ranking.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToProfile = navigateToProfile
            )
        }
        composable(HomeTap.Approval.name) {
            ApprovalScreen(
                navigateToProfile = navigateToProfile
            )
        }
        composable(HomeTap.Ranking.name) { backStackEntry ->
            val homeViewModel = hiltViewModel<HomeViewModel>(backStackEntry)
            RankingScreen(
                homeViewModel = homeViewModel,
                navigateToProfile = navigateToProfile
            )
        }
    }
}