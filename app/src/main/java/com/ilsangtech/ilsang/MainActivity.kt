package com.ilsangtech.ilsang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var splashScreen: SplashScreen
    private val credentialManager = CredentialManager.create(this)
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            var shouldKeep = true
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainActivityViewModel.isLoggedIn.collect { isLoggedIn ->
                        if (isLoggedIn != null) {
                            shouldKeep = false
                        }
                    }
                }
            }
            shouldKeep
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isLoggedIn by mainActivityViewModel.isLoggedIn.collectAsStateWithLifecycle()
            val shouldShowOnBoarding by mainActivityViewModel.shouldShowOnBoarding.collectAsStateWithLifecycle()
            ILSANGApp(
                isLoggedIn = isLoggedIn,
                shouldShowOnBoarding = shouldShowOnBoarding,
                completeOnBoarding = mainActivityViewModel::completeOnBoarding,
                login = {
                    loginWithGoogle(
                        onLoginSuccess = { idToken ->
                            mainActivityViewModel.login(idToken)
                        }
                    )
                }
            )
        }
    }

    private fun loginWithGoogle(
        onLoginSuccess: (idToken: String) -> Unit
    ) {
        val googleIdOption = GetSignInWithGoogleOption
            .Builder(getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@MainActivity
                )
                if (result.credential is CustomCredential) {
                    val credential = result.credential
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        onLoginSuccess(idToken)
                    }
                } else {
                    throw Exception("Invalid credential type")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

