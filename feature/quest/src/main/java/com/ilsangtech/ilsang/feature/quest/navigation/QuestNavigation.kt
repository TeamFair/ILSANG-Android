package com.ilsangtech.ilsang.feature.quest.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.quest.QuestTabScreen
import kotlinx.serialization.Serializable

@Serializable
data object QuestBaseRoute

@Serializable
data object QuestRoute

fun NavGraphBuilder.questNavigation(
    onNavigateToSubmit: (String) -> Unit
) {
    navigation<QuestBaseRoute>(startDestination = QuestRoute) {
        composable<QuestRoute> {
            QuestTabScreen(navigateToSubmit = onNavigateToSubmit)
        }
    }
}