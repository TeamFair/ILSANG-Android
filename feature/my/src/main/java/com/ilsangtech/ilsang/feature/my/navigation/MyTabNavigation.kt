package com.ilsangtech.ilsang.feature.my.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ilsangtech.ilsang.feature.my.MyChallengeScreen
import com.ilsangtech.ilsang.feature.my.MyTitleScreen
import com.ilsangtech.ilsang.feature.my.UserProfileEditScreen
import com.ilsangtech.ilsang.feature.my.screens.customer_center.CustomerCenterScreen
import com.ilsangtech.ilsang.feature.my.screens.faq.FaqScreen
import com.ilsangtech.ilsang.feature.my.SettingScreen
import com.ilsangtech.ilsang.feature.my.TermsScreen
import com.ilsangtech.ilsang.feature.my.WithdrawalScreen
import com.ilsangtech.ilsang.feature.my.component.CustomerCenterScreen
import com.ilsangtech.ilsang.feature.my.screens.mytab.MyTabScreen
import com.ilsangtech.ilsang.feature.my.screens.setting.SettingScreen
import com.ilsangtech.ilsang.feature.my.screens.terms.TermsScreen
import com.ilsangtech.ilsang.feature.my.screens.withdrawal.WithdrawalScreen
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.UserProfileEditScreen
import kotlinx.serialization.Serializable

@Serializable
data object MyBaseRoute

@Serializable
data object SettingRoute

@Serializable
data object WithdrawalRoute

@Serializable
data object FaqRoute

@Serializable
data object CustomerCenterRoute

@Serializable
data object MyRoute

@Serializable
data object MyEditRoute

@Serializable
data class MyChallengeRoute(
    val challengeId: String,
    val receiptImageId: String?,
    val questImageId: String?,
    val title: String,
    val viewCount: Int,
    val likeCount: Int
)

@Serializable
data object TermsRoute

@Serializable
data class MyTitleRoute(
    val titleId: String?
)


fun NavController.navigateToSetting() = navigate(SettingRoute)

fun NavGraphBuilder.myTabNavigation(
    navigateToLogin: () -> Unit,
    navigateToMyTabMain: () -> Unit,
    navigateToNicknameEdit: () -> Unit,
    navigateToMyChallenge: (String, String?, String?, String, Int, Int) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToCustomerCenter: () -> Unit,
    navigateToFaq: () -> Unit,
    navigateToLicense: () -> Unit,
    navigateToTerms: () -> Unit,
    navigateToWithdrawal: () -> Unit,
    navigateToMyTitle: (titleId: String?) -> Unit
) {
    navigation<MyBaseRoute>(startDestination = MyRoute) {
        composable<MyRoute> {
            MyTabScreen(
                onSettingButtonClick = navigateToSetting
            )
        }
        composable<MyEditRoute>(
            enterTransition = {
                slideIntoContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(300),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            UserProfileEditScreen(
                navigateToMyTabMain = navigateToMyTabMain
            )
        }

        composable<MyChallengeRoute> {
            MyChallengeScreen(
                navigateToMyTabMain = navigateToMyTabMain
            )
        }

        composable<SettingRoute> {
            SettingScreen(
                popBackStack = navigateToMyTabMain,
                navigateToLogin = navigateToLogin,
                navigateToCustomerCenter = navigateToCustomerCenter,
                navigateToLicense = navigateToLicense,
                navigateToFaq = navigateToFaq,
                navigateToTerms = navigateToTerms,
                navigateToWithdrawal = navigateToWithdrawal
            )
        }

        composable<FaqRoute> {
            FaqScreen(onBackButtonClick = navigateToMyTabMain)
        }

        composable<WithdrawalRoute> {
            WithdrawalScreen(
                navigateToLogin = navigateToLogin,
                navigateToMyMain = navigateToMyTabMain
            )
        }

        composable<CustomerCenterRoute> {
            CustomerCenterScreen(
                onBackButtonClick = navigateToMyTabMain
            )
        }

        composable<TermsRoute> {
            TermsScreen(
                onBackButtonClick = navigateToMyTabMain
            )
        }
        composable<MyTitleRoute> {
            MyTitleScreen(
                onBackButtonClick = navigateToMyTabMain
            )
        }
    }
}