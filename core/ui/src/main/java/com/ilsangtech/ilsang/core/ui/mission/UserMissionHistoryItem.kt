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
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.core.ui.mission.model.UserMissionHistoryUiModel
import com.ilsangtech.ilsang.core.ui.quest.EventQuestTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.MissionTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.heading02

@Composable
fun UserMissionHistoryItem(
    modifier: Modifier = Modifier,
    userMissionHistory: UserMissionHistoryUiModel,
    onClick: () -> Unit
) {
    if (userMissionHistory.missionType == MissionType.Photo) {
        UserMissionHistoryPhotoItem(
            modifier = modifier,
            userMissionHistory = userMissionHistory,
            onClick = onClick
        )
    } else {
        UserMissionHistoryQuizItem(
            modifier = modifier,
            userMissionHistory = userMissionHistory,
            onClick = onClick
        )
    }
}

@Composable
private fun UserMissionHistoryPhotoItem(
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
                    style = heading02,
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

@Composable
private fun UserMissionHistoryQuizItem(
    modifier: Modifier = Modifier,
    userMissionHistory: UserMissionHistoryUiModel,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = userMissionHistory.title,
                style = heading02
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
                color = gray400
            )
        }
    }
}

@Preview(name = "Photo Mission")
@Composable
private fun UserMissionHistoryItemPhotoPreview() {
    UserMissionHistoryItem(
        userMissionHistory = UserMissionHistoryUiModel(
            missionHistoryId = 1,
            title = "오늘의 하늘 사진 찍기",
            questImageId = null,
            submitImageId = "image_id.jpg",
            viewCount = 100,
            likeCount = 20,
            createdAt = "2024.05.20",
            questType = QuestType.Repeat.Daily,
            missionType = MissionType.Photo
        ),
        onClick = {}
    )
}

@Preview(name = "Quiz Mission")
@Composable
private fun UserMissionHistoryItemQuizPreview() {
    UserMissionHistoryItem(
        userMissionHistory = UserMissionHistoryUiModel(
            missionHistoryId = 2,
            title = "환경 상식 퀴즈",
            questImageId = null,
            submitImageId = null,
            viewCount = 150,
            likeCount = 30,
            createdAt = "2024.05.19",
            questType = QuestType.Event,
            missionType = MissionType.Ox
        ),
        onClick = {}
    )
}