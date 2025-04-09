package com.ilsangtech.ilsang.feature.home

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBar
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBarItem
import com.ilsangtech.ilsang.feature.home.home.HomeTapScreen

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val userNickname by homeViewModel.userNickname.collectAsStateWithLifecycle()
    val homeTapUiState by homeViewModel.homeTapUiState.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            HomeBottomBar(navController)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
            ),
            navController = navController,
            startDestination = HomeTap.Home.name
        ) {
            composable(HomeTap.Home.name) {
                HomeTapScreen(
                    userNickname = userNickname,
                    homeTapUiState = homeTapUiState
                )
            }
            composable(HomeTap.Quest.name) {}
            composable(HomeTap.Approval.name) {}
            composable(HomeTap.Ranking.name) {}
            composable(HomeTap.My.name) {}
        }
    }
}

@Composable
fun HomeBottomBar(
    navController: NavHostController,
) {
    val currentNavBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry.value?.destination?.route

    val homeTaps = HomeTap.entries
    ILSANGNavigationBar {
        homeTaps.forEach { tap ->
            ILSANGNavigationBarItem(
                selected = currentRoute == tap.name,
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
    HomeScreen()
}