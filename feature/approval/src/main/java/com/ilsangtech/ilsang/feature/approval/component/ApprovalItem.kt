package com.ilsangtech.ilsang.feature.approval.component

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import java.io.File

@Composable
internal fun ApprovalItem(
    missionHistory: MissionHistoryUiModel,
    onProfileClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    onHateButtonClick: () -> Unit,
    onReportButtonClick: () -> Unit
) {
    val context = LocalContext.current
    val graphicsLayer = rememberGraphicsLayer()
    var isSharing by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }

    if (showReportDialog) {
        ChallengeReportDialog(
            onReportButtonClick = {
                showReportDialog = false
                onReportButtonClick()
            },
            onDismissRequest = { showReportDialog = false }
        )
    }

    LaunchedEffect(isSharing) {
        if (isSharing) {
            val imageBitmap = graphicsLayer.toImageBitmap()
            val fileName = "approval_item.png"
            val file = File(context.cacheDir, fileName).apply {
                deleteOnExit()
            }
            val outputStream = file.outputStream()

            imageBitmap.asAndroidBitmap()
                .compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setDataAndType(uri, "image/png")
            }
            context.startActivity(Intent.createChooser(sendIntent, "공유하기"))
            isSharing = false
        }
    }

    Surface(
        modifier = Modifier.drawWithContent {
            if (isSharing) {
                graphicsLayer.record {
                    this@drawWithContent.drawContent()
                }
                drawLayer(graphicsLayer)
            } else {
                drawContent()
            }
        },
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
                userProfileImage = missionHistory.user.profileImageId,
                userNickname = missionHistory.user.nickname,
                titleGrade = missionHistory.user.title?.grade,
                titleName = missionHistory.user.title?.name,
                onProfileClick = onProfileClick,
                onShareButtonClick = { isSharing = true },
                onReportButtonClick = { showReportDialog = true }
            )
            ApprovalItemContent(
                title = missionHistory.title,
                challengeImage = missionHistory.submitImageId,
                createdAt = missionHistory.createdAt,
                areaName = missionHistory.commercialAreaName,
                likeCount = missionHistory.likeCount,
                hateCount = missionHistory.hateCount
            )
            if (!isSharing) {
                ApprovalFeedbackButtonRow(
                    isLiked = missionHistory.currentUserEmojis.contains("LIKE"),
                    isHated = missionHistory.currentUserEmojis.contains("HATE"),
                    onLikeButtonClick = onLikeButtonClick,
                    onHateButtonClick = onHateButtonClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun ApprovalItemPreview() {
    val missionHistory = MissionHistoryUiModel(
        commercialAreaName = "강남역",
        createdAt = "2023.10.26 10:00",
        currentUserEmojis = listOf("LIKE"),
        hateCount = 10,
        likeCount = 100,
        missionHistoryId = 1,
        submitImageId = "sample_image_id",
        title = "강남역에서 가장 맛있는 맛집",
        user = MissionHistoryUser(
            userId = "user123",
            nickname = "개발자",
            profileImageId = null,
            title = Title(
                name = "강남역 탐험가",
                grade = TitleGrade.Legend,
                type = TitleType.Commercial
            )
        ),
        viewCount = 1000
    )
    ApprovalItem(
        missionHistory = missionHistory,
        onProfileClick = {},
        onLikeButtonClick = {},
        onHateButtonClick = {},
        onReportButtonClick = {}
    )
}