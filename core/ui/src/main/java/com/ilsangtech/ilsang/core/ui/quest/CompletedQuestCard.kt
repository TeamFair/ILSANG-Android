package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun CompletedQuestCard(
    modifier: Modifier = Modifier,
    quest: BannerQuest,
    onClick: () -> Unit = {}
) {
    CompletedQuestCard(
        modifier = modifier,
        title = quest.title,
        writer = quest.writerName,
        rewardPoints = quest.rewards,
        imageId = quest.imageId,
        isIsZoneQuest = quest.isIsZoneQuest,
        onClick = onClick
    )
}

@Composable
fun CompletedQuestCard(
    modifier: Modifier = Modifier,
    quest: TypedQuest,
    onClick: () -> Unit = {}
) {
    CompletedQuestCard(
        modifier = modifier,
        title = quest.title,
        writer = quest.writerName,
        rewardPoints = quest.rewards,
        imageId = quest.imageId,
        isIsZoneQuest = quest.isIsZoneQuest,
        onClick = onClick
    )
}

@Composable
private fun CompletedQuestCard(
    modifier: Modifier = Modifier,
    title: String,
    writer: String,
    rewardPoints: List<RewardPoint>,
    imageId: String?,
    isIsZoneQuest: Boolean,
    onClick: () -> Unit
) {
    DefaultQuestCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 13.5.dp,
                    vertical = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultQuestContent(
                modifier = Modifier.weight(1f),
                title = title,
                writer = writer,
                rewardPoints = rewardPoints,
                isIsZoneQuest = isIsZoneQuest,
                questImage = {
                    QuestImageWithBadge(
                        imageId = imageId,
                        contentDescription = "퀘스트 이미지"
                    )
                }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFECFAF1))
                ) {
                    Icon(
                        modifier = Modifier.padding(
                            top = 9.dp,
                            bottom = 6.64.dp,
                            start = 6.dp,
                            end = 6.37.dp
                        ),
                        painter = painterResource(R.drawable.icon_completed_quest_checkmark),
                        contentDescription = "퀘스트 완료 체크",
                        tint = Color.Unspecified
                    )
                }
                Text(
                    text = "적립완료",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.dp.toSp(),
                        lineHeight = 16.dp.toSp(),
                        letterSpacing = -0.3.dp.toSp(),
                        color = Color(0xFF3FCC6F)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun CompletedQuestCardNewQuestPreview() {
    CompletedQuestCard(
        quest = TypedQuest(
            expireDate = "2023-12-31",
            favoriteYn = false,
            imageId = "sample_image_id_new",
            mainImageId = "sample_main_image_id_new",
            questId = 123,
            rewards = listOf(
                RewardPoint.Metro(5),
                RewardPoint.Commercial(10),
                RewardPoint.Contribute(15)
            ),
            title = "새로운 퀘스트 타이틀",
            writerName = "새로운 작성자",
            questType = QuestType.Repeat.Daily
        ),
        onClick = {}
    )
}