package com.ilsangtech.ilsang.feature.myzone.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.myzone.MyZoneScreen
import kotlinx.serialization.Serializable

@Serializable
data object MyZoneBaseRoute

@Serializable
data object MyZoneRoute

fun NavGraphBuilder.myZoneNavigation() {
    navigation<MyZoneBaseRoute>(startDestination = MyZoneRoute) {
        composable<MyZoneRoute> {
            MyZoneScreen()
        }
    }
}