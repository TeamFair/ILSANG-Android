package com.ilsangtech.ilsang.feature.approval.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.approval.ApprovalScreen
import kotlinx.serialization.Serializable

@Serializable
data object ApprovalBaseRoute

@Serializable
data object ApprovalRoute

fun NavGraphBuilder.approvalNavigation(
    navigateToProfile: (String) -> Unit
) {
    navigation<ApprovalBaseRoute>(startDestination = ApprovalRoute) {
        composable<ApprovalRoute> {
            ApprovalScreen(navigateToProfile = navigateToProfile)
        }
    }
}