package com.ilsangtech.ilsang

import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseUser
import com.ilsangtech.ilsang.designsystem.theme.ILSANGTheme
import com.ilsangtech.ilsang.navigation.ILSANGNavHost

@Composable
fun ILSANGApp(
    currentUser: FirebaseUser?,
    login: () -> Unit
) {
    ILSANGTheme {
        ILSANGNavHost(
            startDestination = if (currentUser != null) "tutorial" else "login",
            login = login
        )
    }
}