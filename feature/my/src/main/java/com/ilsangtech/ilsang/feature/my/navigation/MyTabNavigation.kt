package com.ilsangtech.ilsang.feature.my.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ilsangtech.ilsang.feature.my.screens.challenge.MyChallengeScreen
import com.ilsangtech.ilsang.feature.my.screens.challenge_detail.MyChallengeDetailScreen
import com.ilsangtech.ilsang.feature.my.screens.customer_center.CustomerCenterScreen
import com.ilsangtech.ilsang.feature.my.screens.faq.FaqScreen
import com.ilsangtech.ilsang.feature.my.screens.favorite_quest.MyFavoriteQuestScreen
import com.ilsangtech.ilsang.feature.my.screens.mytab.MyTabScreen
import com.ilsangtech.ilsang.feature.my.screens.profile_edit.UserProfileEditScreen
import com.ilsangtech.ilsang.feature.my.screens.setting.SettingScreen
import com.ilsangtech.ilsang.feature.my.screens.terms.TermsScreen
import com.ilsangtech.ilsang.feature.my.screens.title.MyTitleScreen
import com.ilsangtech.ilsang.feature.my.screens.withdrawal.WithdrawalScreen
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
data class MyProfileEditRoute(
    val nickname: String,
    val profileImageId: String?
)

@Serializable
data object MyChallengeRoute

@Serializable
data class MyChallengeDetailRoute(
    val missionHistoryId: Int,
    val submitImageId: String,
    val questImageId: String?,
    val title: String,
    val likeCount: Int
)

@Serializable
data object TermsRoute

@Serializable
data class MyTitleRoute(val titleHistoryId: Int?)

@Serializable
data object MyFavoriteQuestRoute

fun NavHostController.navigateToMyProfileEdit(
    nickname: String,
    profileImageId: String?
) = navigate(MyProfileEditRoute(nickname, profileImageId))


fun NavHostController.navigateToMyChallengeDetail(
    missionHistoryId: Int,
    receiptImageId: String,
    questImageId: String?,
    title: String,
    likeCount: Int
) = navigate(
    MyChallengeDetailRoute(
        missionHistoryId = missionHistoryId,
        submitImageId = receiptImageId,
        questImageId = questImageId,
        title = title,
        likeCount = likeCount
    )
)

fun NavController.navigateToSetting() = navigate(SettingRoute)

fun NavHostController.navigateToMyFavoriteQuest() = navigate(MyFavoriteQuestRoute)

fun NavGraphBuilder.myTabNavigation(
    navigateToLogin: () -> Unit,
    navigateToMyTabMain: () -> Unit,
    navigateToMyProfileEdit: (String, String?) -> Unit,
    navigateToMyChallenge: () -> Unit,
    navigateToMyChallengeDetail: (Int, String, String?, String, Int) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToCustomerCenter: () -> Unit,
    navigateToFaq: () -> Unit,
    navigateToLicense: () -> Unit,
    navigateToTerms: () -> Unit,
    navigateToWithdrawal: () -> Unit,
    navigateToMyTitle: (titleHistoryId: Int?) -> Unit,
    navigateToMyFavoriteQuest: () -> Unit,
    navigateToMyZone: () -> Unit,
    navigateToQuestTab: () -> Unit
) {
    navigation<MyBaseRoute>(startDestination = MyRoute) {
        composable<MyRoute> {
            MyTabScreen(
                onSettingButtonClick = navigateToSetting,
                onProfileEditButtonClick = navigateToMyProfileEdit,
                onMissionHistoryButtonClick = navigateToMyChallenge,
                onFavoriteQuestButtonClick = navigateToMyFavoriteQuest,
                onQuestNavButtonClick = navigateToQuestTab,
                onTitleClick = navigateToMyTitle
            )
        }
        composable<MyProfileEditRoute>(
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
                onMissionHistoryClick = { missionHistory ->
                    navigateToMyChallengeDetail(
                        missionHistory.missionHistoryId,
                        missionHistory.submitImageId,
                        missionHistory.questImageId,
                        missionHistory.title,
                        missionHistory.likeCount
                    )
                },
                onQuestNavButtonClick = navigateToQuestTab,
                onBackButtonClick = navigateToMyTabMain
            )
        }

        composable<MyChallengeDetailRoute> {
            MyChallengeDetailScreen(
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
        composable<MyFavoriteQuestRoute> {
            MyFavoriteQuestScreen(
                onMyZoneClick = navigateToMyZone,
                onBackButtonClick = navigateToMyTabMain
            )
        }
    }
}