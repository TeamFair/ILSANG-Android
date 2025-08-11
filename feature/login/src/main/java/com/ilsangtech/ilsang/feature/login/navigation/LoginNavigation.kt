package com.ilsangtech.ilsang.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginBaseRoute

@Serializable
data object LoginRoute

fun NavGraphBuilder.loginNavigation(
    navigateToHome: () -> Unit
) {
    navigation<LoginBaseRoute>(startDestination = LoginRoute) {
        composable<LoginRoute> {
            LoginScreen(login = navigateToHome)
        }
    }
}