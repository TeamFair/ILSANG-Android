package com.ilsangtech.ilsang

import androidx.compose.runtime.Composable
import com.ilsangtech.ilsang.designsystem.theme.ILSANGTheme
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
                    !isLoggedIn -> "login"
                    shouldShowOnBoarding -> "tutorial"
                    else -> "home"
                },
                completeOnBoarding = completeOnBoarding,
                login = login
            )
        }
    }
}