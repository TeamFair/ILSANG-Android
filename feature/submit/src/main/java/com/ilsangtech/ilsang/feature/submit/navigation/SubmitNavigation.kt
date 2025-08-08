package com.ilsangtech.ilsang.feature.submit.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.submit.SubmitScreen
import kotlinx.serialization.Serializable

@Serializable
data object SubmitBaseRoute

@Serializable
data class SubmitRoute(val questId: String)

fun NavGraphBuilder.submitNavigation(popBackStack: () -> Unit) {
    navigation<SubmitBaseRoute>(startDestination = SubmitRoute::class) {
        composable<SubmitRoute> {
            SubmitScreen(onDismiss = popBackStack)
        }
    }
}