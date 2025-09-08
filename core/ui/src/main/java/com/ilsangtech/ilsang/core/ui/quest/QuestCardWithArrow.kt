package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500

@Composable
fun QuestCardWithArrow(
    modifier: Modifier = Modifier,
    quest: LargeRewardQuest,
    onClick: () -> Unit
) {
    QuestCardWithArrow(
        modifier = modifier,
        title = quest.title,
        writer = quest.writerName,
        rewardPoints = quest.rewards,
        imageId = quest.imageId,
        onClick = onClick
    )
}

@Composable
fun QuestCardWithArrow(
    modifier: Modifier = Modifier,
    quest: BannerQuest,
    onClick: () -> Unit
) {
    QuestCardWithArrow(
        modifier = modifier,
        title = quest.title,
        writer = quest.writerName,
        questType = quest.questType,
        rewardPoints = quest.rewards,
        imageId = quest.imageId,
        onClick = onClick
    )
}

@Composable
private fun QuestCardWithArrow(
    modifier: Modifier = Modifier,
    title: String,
    writer: String,
    questType: NewQuestType? = null,
    rewardPoints: List<RewardPoint>,
    imageId: String?,
    onClick: () -> Unit
) {
    DefaultQuestCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 23.dp,
                    vertical = 20.dp
                )
        ) {
            DefaultQuestContent(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                writer = writer,
                rewardPoints = rewardPoints,
                questImage = {
                    if (questType == null) {
                        DefaultQuestImage(
                            imageId = imageId,
                            contentDescription = "퀘스트 이미지"
                        )
                    } else {
                        QuestImageWithBadge(
                            imageId = imageId,
                            questType = questType,
                            contentDescription = "퀘스트 타입 이미지"
                        )
                    }
                }
            )
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
                    .background(Color(0xFFF4F4F4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    painter = painterResource(R.drawable.icon_card_arrow_right),
                    tint = gray500,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuestCardWithArrowPreview() {
    val quest = LargeRewardQuest(
        questId = 1,
        expireDate = "2023-12-31",
        imageId = "sample_image_id",
        mainImageId = "sample_main_image_id",
        rewards = listOf(
            RewardPoint.Metro(5),
            RewardPoint.Commercial(10),
            RewardPoint.Contribute(15)
        ),
        title = "Sample Quest Title",
        writerName = "Sample Writer"
    )
    QuestCardWithArrow(quest = quest, onClick = {})
}

