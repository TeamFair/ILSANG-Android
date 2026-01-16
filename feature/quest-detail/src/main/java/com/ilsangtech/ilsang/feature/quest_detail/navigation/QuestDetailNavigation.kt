package com.ilsangtech.ilsang.feature.quest_detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.quest_detail.QuestDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data object QuestDetailBaseRoute

@Serializable
data class QuestDetailRoute(val questId: Int)

fun NavController.navigateToQuestDetail(questId: Int) {
    navigate(QuestDetailRoute(questId))
}

fun NavGraphBuilder.questDetailNavigation(
    popBackStack: () -> Unit
) {
    navigation<QuestDetailBaseRoute>(startDestination = QuestDetailRoute::class) {
        composable<QuestDetailRoute> {
            QuestDetailScreen(
                onBackButtonClick = popBackStack
            )
        }
    }
}