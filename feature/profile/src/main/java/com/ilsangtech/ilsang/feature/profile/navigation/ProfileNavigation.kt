package com.ilsangtech.ilsang.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ilsangtech.ilsang.feature.profile.ChallengeScreen
import com.ilsangtech.ilsang.feature.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileBaseRoute

@Serializable
data class ProfileRoute(val userId: String)

@Serializable
data class ChallengeRoute(val missionHistoryId: Int)

fun NavHostController.navigateToChallenge(missionHistoryId: Int) =
    navigate(ChallengeRoute(missionHistoryId))

fun NavGraphBuilder.profileRoute(
    navigateToChallenge: (Int) -> Unit,
    popBackStack: () -> Unit
) {
    navigation<ProfileBaseRoute>(
        startDestination = ProfileRoute::class
    ) {
        composable<ProfileRoute> {
            ProfileScreen(
                navigateToChallenge = { navigateToChallenge(it.missionHistoryId) },
                onPopBackStack = popBackStack
            )
        }
        composable<ChallengeRoute> {
            ChallengeScreen(popBackStack = popBackStack)
        }
    }
}