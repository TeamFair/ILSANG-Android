package com.ilsangtech.ilsang.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ilsangtech.ilsang.feature.home.HomeScreen
import com.ilsangtech.ilsang.feature.login.LoginScreen
import com.ilsangtech.ilsang.feature.profile.navigation.ChallengeRoute
import com.ilsangtech.ilsang.feature.profile.navigation.ProfileRoute
import com.ilsangtech.ilsang.feature.profile.navigation.profileRoute
import com.ilsangtech.ilsang.feature.tutorial.TutorialScreen

@Composable
fun ILSANGNavHost(
    startDestination: String,
    login: () -> Unit,
    completeOnBoarding: () -> Unit
) {
    val context = LocalContext.current
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
                navigateToLogin = {
                    Firebase.auth.signOut()
                    navController.navigate("login") {
                        popUpTo("login")
                    }
                },
                navigateToProfile = {
                    navController.navigate(ProfileRoute(it))
                },
                navigateToLicense = {
                    OssLicensesMenuActivity.setActivityTitle("오픈소스 정보")
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                }
            )
        }

        profileRoute(
            navigateToChallenge = {
                navController.navigate(
                    ChallengeRoute(
                        receiptImageId = it.receiptImageId,
                        questImageId = it.questImage,
                        likeCount = it.likeCnt,
                        title = it.missionTitle
                    )
                )
            },
            popBackStack = navController::popBackStack
        )
    }
}