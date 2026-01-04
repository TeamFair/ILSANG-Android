package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.quest.EventQuestTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.MissionTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.approval.model.MissionExecutionUiState

@Composable
internal fun ApprovalQuestContent(
    modifier: Modifier = Modifier,
    questTitle: String,
    writerName: String,
    questType: QuestType,
    missionType: MissionType,
    missionExecutionUiState: MissionExecutionUiState,
    ctaText: String
) {
    val (chipText, chipTextColor) = remember {
        when (missionExecutionUiState) {
            MissionExecutionUiState.Available -> ctaText to gray500
            MissionExecutionUiState.Completed -> "수행 완료" to gray300
            MissionExecutionUiState.Expired -> "기간 만료" to gray300
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (questType is QuestType.Repeat) {
                    RepeatQuestTypeBadge(repeatType = questType)
                } else if (questType is QuestType.Event) {
                    EventQuestTypeBadge()
                }
                MissionTypeBadge(missionType = missionType)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = questTitle,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = writerName,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                ),
                color = gray400
            )
        }
        Row(
            modifier = Modifier
                .height(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = Color(0xFFF6F6F6))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = chipText,
                style = tapBoldTextStyle.copy(
                    fontSize = 14.dp.toSp(),
                    lineHeight = 24.dp.toSp(),
                ),
                color = chipTextColor
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.icon_right),
                tint = gray500,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ApprovalQuestContentPreview() {
    ApprovalQuestContent(
        questTitle = "정자동 최고의 돈까스 가게 가기",
        writerName = "야미돈까스 정자동점",
        questType = QuestType.Repeat.Daily,
        missionType = MissionType.Photo,
        missionExecutionUiState = MissionExecutionUiState.Available,
        ctaText = "바로가기"
    )
}
