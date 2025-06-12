package com.ilsangtech.ilsang.feature.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBar
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBarItem
import com.ilsangtech.ilsang.feature.home.approval.ApprovalScreen
import com.ilsangtech.ilsang.feature.home.home.HomeTapScreen
import com.ilsangtech.ilsang.feature.home.my.navigation.myTabNavigation
import com.ilsangtech.ilsang.feature.home.quest.QuestTabScreen
import com.ilsangtech.ilsang.feature.home.ranking.RankingScreen
import com.ilsangtech.ilsang.feature.home.submit.SubmitScreen

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit
) {
    val navController = rememberNavController()
    val userInfo by homeViewModel.myInfo.collectAsStateWithLifecycle()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val topLevelDestinations = listOf(
        HomeTap.Home.name,
        HomeTap.Quest.name,
        HomeTap.Approval.name,
        HomeTap.Ranking.name,
        "${HomeTap.My.name}/Main"
    )

    Scaffold(
        bottomBar = {
            if (currentDestination?.hierarchy?.any { it.route in topLevelDestinations } == true) {
                HomeBottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
                        - WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            ),
            navController = navController,
            startDestination = HomeTap.Home.name
        ) {
            composable(HomeTap.Home.name) {
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
                        navController.navigate("${HomeTap.My.name}/Main") {
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
            composable(HomeTap.Quest.name) {
                QuestTabScreen(
                    homeViewModel = homeViewModel,
                    navigateToSubmit = {
                        navController.navigate("Submit")
                    }
                )
            }
            composable(HomeTap.Approval.name) {
                ApprovalScreen(
                    navigateToProfile = navigateToProfile
                )
            }
            composable(HomeTap.Ranking.name) {
                RankingScreen(
                    homeViewModel = homeViewModel,
                    navigateToProfile = navigateToProfile
                )
            }
            myTabNavigation(
                homeViewModel = homeViewModel,
                navigateToMyTabMain = {
                    navController.popBackStack()
                },
                navigateToNicknameEdit = { navController.navigate("${HomeTap.My.name}/Edit") },
                navigateToMyChallenge = {
                    navController.navigate("${HomeTap.My.name}/Challenge")
                }
            )
            composable("Submit") {
                SubmitScreen(homeViewModel) {
                    navController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun HomeBottomBar(
    navController: NavHostController,
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val homeTaps = HomeTap.entries
    ILSANGNavigationBar {
        homeTaps.forEach { tap ->
            ILSANGNavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == tap.name } == true,
                icon = {
                    Icon(
                        painter = painterResource(id = tap.defaultIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tap.title,
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = tap.selectedIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tap.title,
                    )
                },
                label = tap.title,
                onClick = {
                    navController.navigate(tap.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}