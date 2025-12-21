package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.feature.approval.model.ExampleMissionHistoryUiModel

@Composable
internal fun ApprovalExampleItem(
    modifier: Modifier = Modifier,
    uiModel: ExampleMissionHistoryUiModel,
    onProfileClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ApprovalItemUserInfo(
                user = uiModel.user,
                onProfileClick = onProfileClick,
                onShareButtonClick = {},
                onReportButtonClick = onReportButtonClick
            )
            ApprovalItemContent(
                challengeImage = uiModel.submitImageId,
                createdAt = uiModel.createdAt,
                areaName = uiModel.commercialAreaName
            )
            ApprovalItemStatsRow(
                likeCount = uiModel.likeCount,
                shareCount = uiModel.shareCount,
                commentCount = uiModel.commentCount,
                isLike = uiModel.currentUserEmojis.contains("LIKE"),
                onLikeButtonClick = onLikeButtonClick
            )
        }
    }
}