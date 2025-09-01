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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ilsangtech.ilsang.designsystem.component.ILSANGNavigationBar
import com.ilsangtech.ilsang.designsystem.component.IlsangNavigationBarItem
import com.ilsangtech.ilsang.feature.approval.navigation.ApprovalExampleRoute
import com.ilsangtech.ilsang.feature.approval.navigation.approvalNavigation
import com.ilsangtech.ilsang.feature.banner.navigation.bannerNavigation
import com.ilsangtech.ilsang.feature.banner.navigation.navigateToBannerDetail
import com.ilsangtech.ilsang.feature.home.navigation.HomeBaseRoute
import com.ilsangtech.ilsang.feature.home.navigation.homeNavigation
import com.ilsangtech.ilsang.feature.iszone.navigation.IsZoneBaseRoute
import com.ilsangtech.ilsang.feature.iszone.navigation.isZoneNavigation
import com.ilsangtech.ilsang.feature.login.navigation.LoginRoute
import com.ilsangtech.ilsang.feature.login.navigation.loginNavigation
import com.ilsangtech.ilsang.feature.my.navigation.CustomerCenterRoute
import com.ilsangtech.ilsang.feature.my.navigation.FaqRoute
import com.ilsangtech.ilsang.feature.my.navigation.MyChallengeRoute
import com.ilsangtech.ilsang.feature.my.navigation.MyTitleRoute
import com.ilsangtech.ilsang.feature.my.navigation.TermsRoute
import com.ilsangtech.ilsang.feature.my.navigation.WithdrawalRoute
import com.ilsangtech.ilsang.feature.my.navigation.myTabNavigation
import com.ilsangtech.ilsang.feature.my.navigation.navigateToMyProfileEdit
import com.ilsangtech.ilsang.feature.my.navigation.navigateToSetting
import com.ilsangtech.ilsang.feature.myzone.navigation.MyZoneBaseRoute
import com.ilsangtech.ilsang.feature.myzone.navigation.myZoneNavigation
import com.ilsangtech.ilsang.feature.profile.navigation.ProfileRoute
import com.ilsangtech.ilsang.feature.profile.navigation.navigateToChallenge
import com.ilsangtech.ilsang.feature.profile.navigation.profileRoute
import com.ilsangtech.ilsang.feature.quest.navigation.questNavigation
import com.ilsangtech.ilsang.feature.ranking.navigation.rankingNavigation
import com.ilsangtech.ilsang.feature.submit.navigation.navigateToSubmit
import com.ilsangtech.ilsang.feature.submit.navigation.submitNavigation
import com.ilsangtech.ilsang.feature.tutorial.navigation.TutorialBaseRoute
import com.ilsangtech.ilsang.feature.tutorial.navigation.tutorialNavigation
import kotlin.reflect.KClass

@Composable
fun IlsangNavHost(
    startDestination: KClass<*>,
    login: () -> Unit,
    completeOnBoarding: () -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    val topLevelDestinations = BottomTab.entries

    Scaffold(
        bottomBar = {
            if (topLevelDestinations.any { currentDestination?.hasRoute(it.route) == true }) {
                BottomBarNavigation(navController)
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
            loginNavigation(navigateToHome = login)

            tutorialNavigation(
                navigateToHome = {
                    completeOnBoarding()
                    navController.navigate(HomeBaseRoute) {
                        popUpTo(TutorialBaseRoute) { inclusive = true }
                    }
                }
            )

            homeNavigation(
                navigateToQuestTab = { navController.navigateToTopLevelDestination(BottomTab.Quest) },
                navigateToMyTab = { navController.navigateToTopLevelDestination(BottomTab.My) },
                navigateToRankingTab = { navController.navigateToTopLevelDestination(BottomTab.Ranking) },
                navigateToProfile = {
                    navController.navigate(ProfileRoute(it))
                },
                navigateToSubmit = navController::navigateToSubmit,
                onBannerClick = navController::navigateToBannerDetail,
                onMyZoneClick = {
                    navController.navigate(MyZoneBaseRoute)
                },
                onIsZoneClick = {
                    navController.navigate(IsZoneBaseRoute)
                },
                onMissionImageClick = { missionId ->
                    navController.navigate(ApprovalExampleRoute(missionId))
                }
            )

            questNavigation(
                onNavigateToSubmit = navController::navigateToSubmit,
                onNavigateToMyZone = {
                    navController.navigate(MyZoneBaseRoute)
                },
                onMissionImageClick = { missionId ->
                    navController.navigate(ApprovalExampleRoute(missionId))
                }
            )

            myTabNavigation(
                navigateToLogin = {
                    Firebase.auth.signOut()
                    navController.navigate(LoginRoute) {
                        popUpTo(HomeBaseRoute) { inclusive = true }
                    }
                },
                navigateToMyTabMain = {
                    navController.popBackStack()
                },
                navigateToMyProfileEdit = navController::navigateToMyProfileEdit,
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
                navigateToSetting = navController::navigateToSetting,
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
                },
                navigateToMyTitle = {
                    navController.navigate(MyTitleRoute(it))
                }
            )

            profileRoute(
                navigateToChallenge = navController::navigateToChallenge,
                popBackStack = navController::popBackStack
            )

            submitNavigation(popBackStack = navController::popBackStack)

            rankingNavigation(
                navigateToRankingDetail = { rankingDetailRoute ->
                    navController.navigate(rankingDetailRoute)
                },
                onBackButtonClick = navController::popBackStack
            )

            approvalNavigation(
                popBackStack = navController::popBackStack,
                navigateToProfile = { id ->
                    navController.navigate(ProfileRoute(id))
                }
            )

            myZoneNavigation(onBackButtonClick = navController::popBackStack)

            isZoneNavigation(onBackButtonClick = navController::popBackStack)

            bannerNavigation(onBackButtonClick = navController::popBackStack)
        }
    }
}

@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val bottomTabs = BottomTab.entries
    ILSANGNavigationBar {
        bottomTabs.forEach { tab ->
            IlsangNavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.hasRoute(tab.route) } == true,
                icon = {
                    Icon(
                        painter = painterResource(id = tab.defaultIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tab.title,
                    )
                },
                selectedIcon = {
                    Icon(
                        painter = painterResource(id = tab.selectedIconRes),
                        tint = Color.Unspecified,
                        contentDescription = tab.title,
                    )
                },
                label = tab.title,
                onClick = { navController.navigateToTopLevelDestination(tab) }
            )
        }
    }
}