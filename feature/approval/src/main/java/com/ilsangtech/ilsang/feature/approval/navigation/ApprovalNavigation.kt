package com.ilsangtech.ilsang.feature.approval.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.feature.approval.ApprovalExampleScreen
import com.ilsangtech.ilsang.feature.approval.ApprovalScreen
import com.ilsangtech.ilsang.feature.approval.ReportScreen
import kotlinx.serialization.Serializable

@Serializable
data object ApprovalBaseRoute

@Serializable
data object ApprovalRoute

@Serializable
data class ApprovalExampleRoute(
    val missionId: Int,
    val title: String,
    val writerName: String,
    val questType: QuestType
)

@Serializable
data class ReportRoute(
    val missionHistoryId: Int? = null,
    val commentId: Int? = null
)

fun NavGraphBuilder.approvalNavigation(
    popBackStack: () -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToMissionReport: (Int) -> Unit
) {
    navigation<ApprovalBaseRoute>(startDestination = ApprovalRoute) {
        composable<ApprovalRoute> {
            ApprovalScreen(
                navigateToProfile = navigateToProfile,
                navigateToReport = navigateToMissionReport
            )
        }
    }
    composable<ApprovalExampleRoute> {
        ApprovalExampleScreen(
            navigateToProfile = navigateToProfile,
            navigateToReport = navigateToMissionReport,
            onBackButtonClick = popBackStack
        )
    }
    composable<ReportRoute> {
        ReportScreen(popBackStack = popBackStack)
    }
}