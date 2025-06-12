package com.ilsangtech.ilsang.feature.profile.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.model.Challenge
import com.ilsangtech.ilsang.feature.profile.ChallengeScreen
import com.ilsangtech.ilsang.feature.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileBaseRoute

@Serializable
data class ProfileRoute(val userId: String)

@Serializable
data class ChallengeRoute(
    val receiptImageId: String?,
    val questImageId: String?,
    val likeCount: Int,
    val title: String
)

fun NavGraphBuilder.profileRoute(
    navigateToChallenge: (Challenge) -> Unit,
    popBackStack: () -> Unit
) {
    navigation<ProfileBaseRoute>(
        startDestination = ProfileRoute::class
    ) {
        composable<ProfileRoute> {
            ProfileScreen(
                navigateToChallenge = navigateToChallenge,
                onPopBackStack = popBackStack
            )
        }

        composable<ChallengeRoute> {
            val receiptImageId = it.toRoute<ChallengeRoute>().receiptImageId
            val questImageId = it.toRoute<ChallengeRoute>().questImageId
            val likeCount = it.toRoute<ChallengeRoute>().likeCount
            val title = it.toRoute<ChallengeRoute>().title

            ChallengeScreen(
                receiptImageId = receiptImageId,
                questImageId = questImageId,
                likeCount = likeCount,
                title = title,
                popBackStack = popBackStack
            )
        }
    }
}