package com.ilsangtech.ilsang.feature.approval.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.feature.approval.ApprovalDetailScreen
import com.ilsangtech.ilsang.feature.approval.ApprovalExampleScreen
import com.ilsangtech.ilsang.feature.approval.ApprovalScreen
import com.ilsangtech.ilsang.feature.approval.ReportScreen
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Serializable
data object ApprovalBaseRoute

@Serializable
data object ApprovalRoute

@Serializable
data class ApprovalExampleRoute(
    val missionId: Int,
    val questId: Int,
    val title: String,
    val writerName: String,
    val questType: QuestType,
    val isIsZoneQuest: Boolean
)

@Serializable
data class ReportRoute(
    val missionHistoryId: Int? = null,
    val commentId: Int? = null
)

@Serializable
data class ApprovalDetailRoute(val missionHistory: MissionHistoryUiModel)


fun NavController.navigateToApprovalDetail(missionHistory: MissionHistoryUiModel) {
    navigate(ApprovalDetailRoute(missionHistory))
}

fun NavGraphBuilder.approvalNavigation(
    popBackStack: () -> Unit,
    navigateToApprovalDetail: (MissionHistoryUiModel) -> Unit,
    navigateToQuestTab: (Int) -> Unit,
    navigateToImageCapture: (Int, Int, Boolean) -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToMissionReport: (Int) -> Unit,
    navigateToCommentReport: (Int) -> Unit
) {
    navigation<ApprovalBaseRoute>(startDestination = ApprovalRoute) {
        composable<ApprovalRoute> {
            ApprovalScreen(
                navigateToApprovalDetail = navigateToApprovalDetail,
                navigateToQuestTab = navigateToQuestTab,
                navigateToProfile = navigateToProfile,
                navigateToReport = navigateToMissionReport
            )
        }
    }
    composable<ApprovalExampleRoute>(questTypeMap) {
        ApprovalExampleScreen(
            navigateToImageCapture = navigateToImageCapture,
            navigateToProfile = navigateToProfile,
            navigateToReport = navigateToMissionReport,
            onBackButtonClick = popBackStack
        )
    }
    composable<ReportRoute> {
        ReportScreen(popBackStack = popBackStack)
    }
    composable<ApprovalDetailRoute>(missionHistoryUiModelTypeMap) {
        ApprovalDetailScreen(
            onBackButtonClick = popBackStack,
            onProfileClick = navigateToProfile,
            onMissionHistoryReportClick = navigateToMissionReport,
            onCommentReportClick = navigateToCommentReport
        )
    }
}

private val questTypeNavType =
    object : NavType<QuestType>(isNullableAllowed = false) {
        override fun get(
            bundle: Bundle,
            key: String
        ): QuestType? {
            return bundle.getString(key)?.let { Json.decodeFromString(it) }
        }

        override fun parseValue(value: String): QuestType {
            return Json.decodeFromString(value)
        }

        override fun put(
            bundle: Bundle,
            key: String,
            value: QuestType
        ) {
            bundle.putString(key, Json.encodeToString(QuestType.serializer(), value))
        }

        override fun serializeAsValue(value: QuestType): String {
            return Json.encodeToString(QuestType.serializer(), value)
        }
    }

private val missionHistoryUiModelNavType =
    object : NavType<MissionHistoryUiModel>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): MissionHistoryUiModel? {
            return bundle.getString(key)?.let {
                Json.decodeFromString(Uri.decode(it))
            }
        }

        override fun parseValue(value: String): MissionHistoryUiModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: MissionHistoryUiModel) {
            bundle.putString(key, Uri.encode(Json.encodeToString(value)))
        }

        override fun serializeAsValue(value: MissionHistoryUiModel): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
internal val questTypeMap = mapOf(typeOf<QuestType>() to questTypeNavType)
internal val missionHistoryUiModelTypeMap =
    mapOf(typeOf<MissionHistoryUiModel>() to missionHistoryUiModelNavType)