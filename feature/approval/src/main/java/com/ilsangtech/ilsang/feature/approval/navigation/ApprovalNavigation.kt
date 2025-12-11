package com.ilsangtech.ilsang.feature.approval.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.feature.approval.ApprovalExampleScreen
import com.ilsangtech.ilsang.feature.approval.ApprovalScreen
import com.ilsangtech.ilsang.feature.approval.ReportScreen
import kotlinx.serialization.Serializable

@Serializable
data object ApprovalBaseRoute

@Serializable
data object ApprovalRoute

@Serializable
data class ApprovalExampleRoute(val missionId: Int)

@Serializable
data class ReportRoute(val missionHistoryId: Int)

fun NavGraphBuilder.approvalNavigation(
    popBackStack: () -> Unit,
    navigateToProfile: (String) -> Unit
) {
    navigation<ApprovalBaseRoute>(startDestination = ApprovalRoute) {
        composable<ApprovalRoute> {
            ApprovalScreen(navigateToProfile = navigateToProfile)
        }
    }
    composable<ApprovalExampleRoute> {
        ApprovalExampleScreen(onBackButtonClick = popBackStack)
    }
    composable<ReportRoute> {
        ReportScreen()
    }
}