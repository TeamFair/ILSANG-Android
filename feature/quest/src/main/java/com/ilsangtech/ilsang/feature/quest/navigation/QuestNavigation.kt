package com.ilsangtech.ilsang.feature.quest.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.quest.QuestTabScreen

fun NavGraphBuilder.questNavigation(
    onNavigateToSubmit: (String) -> Unit
) {
    navigation(
        route = "QuestBaseRoute",
        startDestination = "Quest"
    ) {
        composable("Quest") {
            QuestTabScreen(navigateToSubmit = onNavigateToSubmit)
        }
    }
}