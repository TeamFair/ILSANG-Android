package com.ilsangtech.ilsang.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class ProfileRoute(val userId: String)

fun NavGraphBuilder.profileRoute(popBackStack: () -> Unit) {
    composable<ProfileRoute> {}
}