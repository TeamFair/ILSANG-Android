package com.ilsangtech.ilsang.feature.home.my.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ilsangtech.ilsang.feature.home.HomeTap
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.my.MyChallengeScreen
import com.ilsangtech.ilsang.feature.home.my.MyTabScreen
import com.ilsangtech.ilsang.feature.home.my.SettingScreen
import com.ilsangtech.ilsang.feature.home.my.UserProfileEditScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingRoute

fun NavGraphBuilder.myTabNavigation(
    homeViewModel: HomeViewModel,
    navigateToMyTabMain: () -> Unit,
    navigateToNicknameEdit: () -> Unit,
    navigateToMyChallenge: () -> Unit,
    navigateToSetting: () -> Unit
) {
    navigation(
        route = HomeTap.My.name,
        startDestination = "${HomeTap.My.name}/Main",
    ) {
        composable("${HomeTap.My.name}/Main") {
            MyTabScreen(
                homeViewModel = homeViewModel,
                navigateToNicknameEdit = navigateToNicknameEdit,
                navigateToMyChallenge = navigateToMyChallenge,
                navigateToSetting = navigateToSetting
            )
        }
        composable(
            route = "${HomeTap.My.name}/Edit",
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            UserProfileEditScreen(
                homeViewModel = homeViewModel,
                navigateToMyTabMain = navigateToMyTabMain
            )
        }

        composable("${HomeTap.My.name}/Challenge") {
            MyChallengeScreen(
                homeViewModel = homeViewModel,
                navigateToMyTabMain = navigateToMyTabMain
            )
        }

        composable<SettingRoute> {
            SettingScreen(popBackStack = navigateToMyTabMain)
        }
    }
}