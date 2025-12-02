package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
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

@Composable
internal fun ApprovalExampleCtaCard(
    modifier: Modifier = Modifier,
    questTitle: String,
    writerName: String,
    questType: QuestType,
    missionType: MissionType,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.drawBehind {
            drawLine(
                color = gray100,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                color = gray100,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 1.dp.toPx()
            )
        },
        color = Color.White,
        onClick = onClick
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