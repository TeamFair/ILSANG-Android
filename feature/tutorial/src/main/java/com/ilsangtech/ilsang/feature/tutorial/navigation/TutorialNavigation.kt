package com.ilsangtech.ilsang.feature.tutorial.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.tutorial.TutorialScreen
import kotlinx.serialization.Serializable

@Serializable
data object TutorialBaseRoute

@Serializable
data object TutorialRoute

fun NavGraphBuilder.tutorialNavigation(
    navigateToHome: () -> Unit
) {
    navigation<TutorialBaseRoute>(startDestination = TutorialRoute) {
        composable<TutorialRoute> {
            TutorialScreen(navigateToHome = navigateToHome)
        }
    }
}