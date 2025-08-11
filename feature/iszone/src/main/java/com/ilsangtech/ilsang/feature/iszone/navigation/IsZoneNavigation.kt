package com.ilsangtech.ilsang.feature.iszone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.iszone.IsZoneScreen
import kotlinx.serialization.Serializable

@Serializable
data object IsZoneBaseRoute

@Serializable
data object IsZoneRoute

fun NavGraphBuilder.isZoneNavigation(
    onBackButtonClick: () -> Unit
) {
    navigation<IsZoneBaseRoute>(startDestination = IsZoneRoute) {
        composable<IsZoneRoute> {
            IsZoneScreen(onBackButtonClick = onBackButtonClick)
        }
    }
}