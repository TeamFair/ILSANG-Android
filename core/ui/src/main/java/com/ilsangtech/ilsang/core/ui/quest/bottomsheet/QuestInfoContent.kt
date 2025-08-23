package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.mission.Mission
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.ui.BuildConfig
import com.ilsangtech.ilsang.core.ui.quest.EventQuestTypeBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary100
import com.ilsangtech.ilsang.designsystem.theme.title02

@Composable
internal fun QuestInfoContent(
    modifier: Modifier = Modifier,
    quest: QuestDetail
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F5FF)),
            model = BuildConfig.IMAGE_URL + quest.imageId,
            contentDescription = quest.title
        )
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (quest.questType is NewQuestType.Repeat) {
                    RepeatQuestTypeBadge(repeatType = quest.questType as NewQuestType.Repeat)
                } else if (quest.questType is NewQuestType.Event) {
                    EventQuestTypeBadge()
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = quest.title,
                style = title02,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = DateConverter.formatDate(
                    input = "",
                    outputPattern = "MM.dd"
                ) + " ~ " + DateConverter.formatDate(
                    input = quest.expireDate,
                    outputPattern = "MM.dd"
                ),
                style = caption02,
                color = gray400
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = primary100,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                text = "${quest.rewards.sumOf { it.point }}P",
                style = heading01,
                color = primary,
            )
        }
    }
}

@Composable
internal fun QuestInfoContent(
    modifier: Modifier = Modifier,
    quest: Quest
) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFF1F5FF)),
            model = BuildConfig.IMAGE_URL + quest.imageId,
            contentDescription = quest.missionTitle
        )
        Spacer(Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            //TODO 퀘스트 유형 칩 UI 적용
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = quest.missionTitle,
                style = title02,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = DateConverter.formatDate(
                    input = quest.createDate,
                    outputPattern = "MM.dd"
                ) + " ~ " + DateConverter.formatDate(
                    input = quest.expireDate,
                    outputPattern = "MM.dd"
                ),
                style = caption02,
                color = gray400
            )
        }
        Box(
            modifier = Modifier
                .background(
                    color = primary100,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                text = "${quest.rewardList.sumOf { it.quantity }}P",
                style = heading01,
                color = primary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun QuestInfoContentPreview() {
    val quest = Quest(
        createDate = "2023-10-27",
        creatorRole = "USER",
        expireDate = "2023-11-27",
        favoriteYn = false,
        imageId = "sample_image_id",
        mainImageId = "sample_main_image_id",
        marketId = "sample_market_id",
        missionId = "sample_mission_id",
        missionTitle = "일상 공유하고 포인트 받기",
        missionType = "GENERAL",
        popularYn = false,
        questId = "sample_quest_id",
        rewardList = listOf(
            Reward(
                content = null,
                discountRate = null,
                quantity = 10,
                questId = "sample_quest_id",
                rewardId = "reward1",
                target = null,
                title = null,
                type = "POINT"
            ),
            Reward(
                content = null,
                discountRate = null,
                quantity = 20,
                questId = "sample_quest_id",
                rewardId = "reward2",
                target = null,
                title = null,
                type = "POINT"
            )
        ),
        score = 100,
        status = "ACTIVE",
        target = "ALL",
        type = "DAILY",
        writer = "user123"
    )
    QuestInfoContent(quest = quest)
}

@Preview(showBackground = true)
@Composable
internal fun QuestDetailInfoContentPreview() {
    val questDetail = QuestDetail(
        id = 1,
        expireDate = "2023-12-31",
        favoriteYn = true,
        imageId = "sample_image_id",
        mainImageId = "sample_main_image_id",
        missions = listOf(
            Mission(
                id = 101,
                exampleImageIds = listOf("example1.jpg", "example2.jpg"),
                title = "Complete task A",
                type = "TASK"
            ),
            Mission(
                id = 102,
                exampleImageIds = listOf("example3.jpg"),
                title = "Review document B",
                type = "REVIEW"
            )
        ),
        questType = NewQuestType.Event,
        rewards = listOf(
            RewardPoint.Metro(2),
            RewardPoint.Commercial(5),
            RewardPoint.Contribute(10)
        ),
        title = "일상 공유하고 포인트 받기",
        userRank = 5,
        writerName = "사장님 이름"
    )
    QuestInfoContent(
        modifier = Modifier,
        quest = questDetail
    )
}