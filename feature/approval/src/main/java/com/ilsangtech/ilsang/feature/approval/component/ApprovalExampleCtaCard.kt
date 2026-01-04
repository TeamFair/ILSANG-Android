package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.feature.approval.model.MissionExecutionUiState

@Composable
internal fun ApprovalExampleCtaCard(
    modifier: Modifier = Modifier,
    questTitle: String,
    writerName: String,
    questType: QuestType,
    missionType: MissionType = MissionType.Photo,
    missionExecutionUiState: MissionExecutionUiState = MissionExecutionUiState.Available,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.White)
            .drawBehind {
                drawLine(
                    color = gray100,
                    start = Offset(0f, 1.dp.toPx()),
                    end = Offset(size.width, 1.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
                drawLine(
                    color = gray100,
                    start = Offset(0f, size.height - 1.dp.toPx()),
                    end = Offset(size.width, size.height - 1.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = {
                    if (missionExecutionUiState is MissionExecutionUiState.Available) {
                        onClick()
                    }
                }
            ),
    ) {
        ApprovalQuestContent(
            modifier = Modifier.padding(
                vertical = 16.dp,
                horizontal = 20.dp
            ),
            questTitle = questTitle,
            writerName = writerName,
            questType = questType,
            missionType = missionType,
            missionExecutionUiState = missionExecutionUiState,
            ctaText = "바로가기"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ApprovalExampleCtaCardPreview() {
    ApprovalExampleCtaCard(
        modifier = Modifier.padding(16.dp),
        questTitle = "일상이 즐거워지는 퀘스트",
        writerName = "일상",
        questType = QuestType.Repeat.Weekly,
        missionType = MissionType.Photo,
        onClick = {}
    )
}