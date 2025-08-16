package com.ilsangtech.ilsang.feature.approval.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.approval.ApprovalExampleScreen
import com.ilsangtech.ilsang.feature.approval.ApprovalScreen
import kotlinx.serialization.Serializable

@Serializable
data object ApprovalBaseRoute

@Serializable
data object ApprovalRoute

@Serializable
data object ApprovalExampleRoute

fun NavGraphBuilder.approvalNavigation(
    navigateToProfile: (String) -> Unit
) {
    navigation<ApprovalBaseRoute>(startDestination = ApprovalRoute) {
        composable<ApprovalRoute> {
            ApprovalScreen(navigateToProfile = navigateToProfile)
        }
        composable<ApprovalExampleRoute> {
            ApprovalExampleScreen {}
        }
    }
}