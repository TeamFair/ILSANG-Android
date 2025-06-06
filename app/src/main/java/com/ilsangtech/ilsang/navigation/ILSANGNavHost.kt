package com.ilsangtech.ilsang.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilsangtech.ilsang.feature.home.HomeScreen
import com.ilsangtech.ilsang.feature.login.LoginScreen
import com.ilsangtech.ilsang.feature.profile.navigation.ProfileRoute
import com.ilsangtech.ilsang.feature.profile.navigation.profileRoute
import com.ilsangtech.ilsang.feature.tutorial.TutorialScreen

@Composable
fun ILSANGNavHost(
    startDestination: String,
    login: () -> Unit,
    completeOnBoarding: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(login = login)
        }

        composable("tutorial") {
            TutorialScreen {
                completeOnBoarding()
                navController.navigate("home") {
                    popUpTo("tutorial") { inclusive = true }
                }
            }
        }
        composable("home") {
            HomeScreen(
                navigateToProfile = {
                    navController.navigate(ProfileRoute(it))
                }
            )
        }

        profileRoute(popBackStack = navController::popBackStack)
    }
}