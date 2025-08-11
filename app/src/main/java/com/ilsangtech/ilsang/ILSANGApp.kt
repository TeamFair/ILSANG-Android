package com.ilsangtech.ilsang

import androidx.compose.runtime.Composable
import com.ilsangtech.ilsang.designsystem.theme.ILSANGTheme
import com.ilsangtech.ilsang.feature.home.HomeBaseRoute
import com.ilsangtech.ilsang.feature.login.navigation.LoginBaseRoute
import com.ilsangtech.ilsang.feature.tutorial.navigation.TutorialBaseRoute
import com.ilsangtech.ilsang.navigation.ILSANGNavHost

@Composable
fun ILSANGApp(
    isLoggedIn: Boolean?,
    shouldShowOnBoarding: Boolean,
    completeOnBoarding: () -> Unit,
    login: () -> Unit
) {
    ILSANGTheme {
        isLoggedIn?.let {
            ILSANGNavHost(
                startDestination =
                    when {
                        !isLoggedIn -> LoginBaseRoute::class
                        shouldShowOnBoarding -> TutorialBaseRoute::class
                        else -> HomeBaseRoute::class
                    },
                completeOnBoarding = completeOnBoarding,
                login = login
            )
        }
    }
}