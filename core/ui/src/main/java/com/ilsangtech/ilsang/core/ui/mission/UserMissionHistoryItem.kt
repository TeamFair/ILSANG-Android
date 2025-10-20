package com.ilsangtech.ilsang.core.ui.mission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
import com.ilsangtech.ilsang.core.ui.quest.EventQuestTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.MissionTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.title01

@Composable
fun UserMissionHistoryItem(
    modifier: Modifier = Modifier,
    userMissionHistory: UserMissionHistoryUiModel,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(172.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = BuildConfig.IMAGE_URL + userMissionHistory.submitImageId,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 20.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = userMissionHistory.title,
                    style = title01,
                    color = Color.White
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (userMissionHistory.questType is QuestType.Repeat) {
                        RepeatQuestTypeBadge(repeatType = userMissionHistory.questType)
                    } else if (userMissionHistory.questType is QuestType.Event) {
                        EventQuestTypeBadge()
                    }
                    MissionTypeBadge(missionType = userMissionHistory.missionType)
                }
                Text(
                    text = userMissionHistory.createdAt,
                    style = caption01,
                    color = gray200
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
private fun UserMissionHistoryItemPreview() {
    val userMissionHistory = UserMissionHistoryUiModel(
        missionHistoryId = 1,
        title = "겨울 간식 먹기",
        submitImageId = "sample_image_id",
        createdAt = "2025.10.27",
        likeCount = 10,
        viewCount = 100,
        questImageId = null
    )
    UserMissionHistoryItem(userMissionHistory = userMissionHistory, onClick = {})
}
