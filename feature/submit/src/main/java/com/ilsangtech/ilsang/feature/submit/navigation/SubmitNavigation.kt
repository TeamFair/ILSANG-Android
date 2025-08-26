package com.ilsangtech.ilsang.feature.submit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.submit.ImageSubmitScreen
import kotlinx.serialization.Serializable

@Serializable
data object SubmitBaseRoute

@Serializable
data class ImageSubmitRoute(
    val questId: Int,
    val missionId: Int
)

@Serializable
data class OxQuizSubmitRoute(
    val questId: Int,
    val missionId: Int
)

@Serializable
data class WordsQuizSubmitRoute(
    val questId: Int,
    val missionId: Int
)

fun NavGraphBuilder.submitNavigation(popBackStack: () -> Unit) {
    navigation<SubmitBaseRoute>(startDestination = ImageSubmitRoute::class) {
        composable<ImageSubmitRoute> {
            ImageSubmitScreen(onDismiss = popBackStack)
        }
    }
}