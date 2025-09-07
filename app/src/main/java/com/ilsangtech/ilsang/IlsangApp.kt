package com.ilsangtech.ilsang

import androidx.compose.runtime.Composable
import com.ilsangtech.ilsang.designsystem.theme.ILSANGTheme
import com.ilsangtech.ilsang.feature.home.navigation.HomeBaseRoute
import com.ilsangtech.ilsang.feature.login.navigation.LoginBaseRoute
import com.ilsangtech.ilsang.feature.tutorial.navigation.TutorialBaseRoute
import com.ilsangtech.ilsang.navigation.IlsangNavHost

@Composable
fun IlsangApp(
    isLoggedIn: Boolean?,
    shouldShowOnBoarding: Boolean,
    shouldShowIsZoneDialog: Boolean,
    completeOnBoarding: () -> Unit,
    shownIsZoneDialog: (Boolean) -> Unit,
    login: () -> Unit,
    logout: () -> Unit
) {
    ILSANGTheme {
        isLoggedIn?.let {
            IlsangNavHost(
                startDestination =
                    when {
                        !isLoggedIn -> LoginBaseRoute::class
                        shouldShowOnBoarding -> TutorialBaseRoute::class
                        else -> HomeBaseRoute::class
                    },
                shouldShowIsZoneDialog = shouldShowIsZoneDialog,
                completeOnBoarding = completeOnBoarding,
                shownIsZoneDialog = shownIsZoneDialog,
                login = login,
                logout = logout
            )
        }
    }
}