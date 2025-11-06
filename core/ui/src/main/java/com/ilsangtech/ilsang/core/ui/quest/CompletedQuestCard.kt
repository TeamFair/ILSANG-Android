package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.ui.quest.model.BannerQuestUiModel
import com.ilsangtech.ilsang.core.ui.quest.model.TypedQuestUiModel
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun CompletedQuestCard(
    modifier: Modifier = Modifier,
    quest: BannerQuestUiModel,
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
    quest: TypedQuestUiModel,
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
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val isSmallSize = remember { screenWidth < 400 }

    val contentHorizontalPadding = if (isSmallSize) 18.dp else 23.dp
    val contentVerticalPadding = if (isSmallSize) 16.dp else 20.dp

    val completeTextStyle = TextStyle(
        fontFamily = pretendardFontFamily,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF3FCC6F)
    ).copy(
        fontSize = if (isSmallSize) 9.6.dp.toSp() else 12.dp.toSp(),
        lineHeight = if (isSmallSize) 12.8.dp.toSp() else 16.dp.toSp(),
        letterSpacing = if (isSmallSize) -0.24.dp.toSp() else -0.3.dp.toSp()
    )

    DefaultQuestCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = contentHorizontalPadding,
                    vertical = contentVerticalPadding
                )
        ) {
            DefaultQuestContent(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
                isSmallSize = isSmallSize,
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
                modifier = Modifier.align(if (isSmallSize) Alignment.Top else Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier.size(if (isSmallSize) 20.dp else 26.dp),
                    painter = painterResource(R.drawable.icon_checkmark_circle),
                    contentDescription = "퀘스트 완료 체크",
                    tint = Color.Unspecified
                )
                Text(
                    text = "적립완료",
                    style = completeTextStyle
                )
            }
        }
    }
}

@Preview
@Composable
private fun CompletedQuestCardNewQuestPreview() {
    CompletedQuestCard(
        quest = TypedQuestUiModel(
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