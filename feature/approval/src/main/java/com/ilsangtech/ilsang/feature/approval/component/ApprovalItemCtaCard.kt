package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.designsystem.theme.gray100

@Composable
internal fun ApprovalItemCtaCard(
    modifier: Modifier = Modifier,
    questTitle: String,
    writerName: String,
    questType: QuestType,
    missionType: MissionType = MissionType.Photo,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = BorderStroke(width = 1.dp, color = gray100),
        onClick = onClick
    ) {
        ApprovalQuestContent(
            modifier = Modifier.padding(16.dp),
            questTitle = questTitle,
            writerName = writerName,
            questType = questType,
            missionType = missionType,
            ctaText = "나도 하기"
        )
    }
}

@Preview
@Composable
private fun ApprovalItemCtaCardPreview() {
    ApprovalItemCtaCard(
        questTitle = "정자동 최고의 돈까스 가게 가기",
        writerName = "야미돈까스 정자동점",
        questType = QuestType.Repeat.Daily,
        missionType = MissionType.Photo,
        onClick = {}
    )
}