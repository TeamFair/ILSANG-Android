package com.ilsangtech.ilsang.navigation

import android.content.Intent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBar
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBarItem
import com.ilsangtech.ilsang.feature.home.HomeTap
import com.ilsangtech.ilsang.feature.home.homeNavigation
import com.ilsangtech.ilsang.feature.login.LoginScreen
import com.ilsangtech.ilsang.feature.my.navigation.CustomerCenterRoute
import com.ilsangtech.ilsang.feature.my.navigation.FaqRoute
import com.ilsangtech.ilsang.feature.my.navigation.MyChallengeRoute
import com.ilsangtech.ilsang.feature.my.navigation.SettingRoute
import com.ilsangtech.ilsang.feature.my.navigation.TermsRoute
import com.ilsangtech.ilsang.feature.my.navigation.WithdrawalRoute
import com.ilsangtech.ilsang.feature.my.navigation.myTabNavigation
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

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val topLevelDestinations = listOf(
        HomeTap.Home.name,
        HomeTap.Quest.name,
        HomeTap.Approval.name,
        HomeTap.Ranking.name,
        HomeTap.My.name
    )

    Scaffold(
        bottomBar = {
            if (currentDestination?.hierarchy?.any { it.route in topLevelDestinations } == true) {
                HomeBottomBar(navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
                        - WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            ),
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

            homeNavigation(
                navController = navController,
                navigateToProfile = {
                    navController.navigate(ProfileRoute(it))
                }
            )

            myTabNavigation(
                navigateToLogin = {
                    Firebase.auth.signOut()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                navigateToMyTabMain = {
                    navController.popBackStack()
                },
                navigateToNicknameEdit = { navController.navigate("${HomeTap.My.name}/Edit") },
                navigateToMyChallenge = { id, receiptImageId, questImageId, title, viewCount, likeCount ->
                    navController.navigate(
                        MyChallengeRoute(
                            challengeId = id,
                            receiptImageId = receiptImageId,
                            questImageId = questImageId,
                            title = title,
                            likeCount = likeCount,
                            viewCount = viewCount,
                        )
                    )
                },
                navigateToSetting = {
                    navController.navigate(SettingRoute)
                },
                navigateToCustomerCenter = {
                    navController.navigate(CustomerCenterRoute)
                },
                navigateToFaq = {
                    navController.navigate(FaqRoute)
                },
                navigateToLicense = {
                    OssLicensesMenuActivity.setActivityTitle("오픈소스 정보")
                    context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                },
                navigateToTerms = {
                    navController.navigate(TermsRoute)
                },
                navigateToWithdrawal = {
                    navController.navigate(WithdrawalRoute)
                }
            )

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
}

@Composable
fun HomeBottomBar(
    navController: NavHostController,
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val homeTaps = HomeTap.entries
    ILSANGNavigationBar {
        homeTaps.forEach { tap ->
            ILSANGNavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == tap.name } == true,
                icon = {
                    Icon(
                        painter = painterResource(id = tap.defaultIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tap.title,
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = tap.selectedIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tap.title,
                    )
                },
                label = tap.title,
                onClick = {
                    navController.navigate(tap.name) {
                        popUpTo(HomeTap.Home.name) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}