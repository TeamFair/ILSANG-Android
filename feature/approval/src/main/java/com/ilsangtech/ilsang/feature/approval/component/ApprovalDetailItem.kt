package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel

@Composable
internal fun ApprovalDetailItem(
    modifier: Modifier = Modifier,
    missionHistory: MissionHistoryUiModel,
    onProfileClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit,
    onCtaButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp, bottom = 32.dp)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ApprovalItemUserInfo(
            user = missionHistory.user,
            onProfileClick = onProfileClick,
            onShareButtonClick = onShareButtonClick,
            onReportButtonClick = onReportButtonClick
        )
        ApprovalItemContent(
            challengeImage = missionHistory.submitImageId,
            createdAt = missionHistory.createdAt,
            areaName = missionHistory.commercialAreaName
        )
        ApprovalItemCtaCard(
            questTitle = missionHistory.title,
            writerName = missionHistory.writerName,
            questType = missionHistory.questType,
            missionExecutionUiState = missionHistory.missionExecutionUiState,
            onClick = onCtaButtonClick
        )
    }
}
