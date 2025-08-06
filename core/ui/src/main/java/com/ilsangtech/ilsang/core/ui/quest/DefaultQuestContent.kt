package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.ui.R
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
fun DefaultQuestContent(
    modifier: Modifier = Modifier,
    quest: Quest,
    questImage: @Composable () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        questImage()
        Spacer(Modifier.width(20.dp))
        Column {
            Text(
                text = quest.missionTitle,
                maxLines = 1,
                style = defaultQuestContentTitleStyle,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = quest.writer,
                maxLines = 1,
                style = defaultQuestContentWriterStyle,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            RewardChips(quest.rewardList)
        }
    }
}

@Composable
private fun RewardChips(rewardList: List<Reward>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rewardList
            .chunked(3)
            .forEach { chunk ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    chunk.forEach { reward ->
                        RewardChip(reward)
                    }
                }
            }
    }
}

@Composable
private fun RewardChip(reward: Reward) {
    Box(
        modifier = Modifier
            .height(25.dp)
            .border(
                width = 1.dp,
                color = primary,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(
                    when (reward.content) {
                        "INTELLECT" -> R.drawable.bulb
                        "SOCIABILITY" -> R.drawable.social
                        "STRENGTH" -> R.drawable.strength
                        "FUN" -> R.drawable.`fun`
                        else -> R.drawable.heart
                    }
                ),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Text(
                text = "${reward.quantity}",
                style = caption02,
                color = primary
            )
            Spacer(Modifier.width(1.dp))
            Text(
                text = "P",
                style = caption02,
                color = primary
            )
        }
    }
}

private val defaultQuestContentTitleStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 20.sp,
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold
)

private val defaultQuestContentWriterStyle = TextStyle(
    fontSize = 11.sp,
    lineHeight = 16.sp,
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    color = gray400
)

@Preview(showBackground = true)
@Composable
private fun DefaultQuestContentPreview() {
    val quest = Quest(
        createDate = "2023-10-26",
        creatorRole = "USER",
        expireDate = "2023-11-26",
        favoriteYn = false,
        imageId = "sample_image_id",
        mainImageId = "sample_main_image_id",
        marketId = "sample_market_id",
        missionId = "sample_mission_id",
        missionTitle = "퀘스트 타이틀 타이틀",
        missionType = "DAILY",
        popularYn = false,
        questId = "sample_quest_id",
        rewardList = listOf(
            Reward(
                content = "INTELLECT",
                discountRate = null,
                quantity = 10,
                questId = "sample_quest_id",
                rewardId = "reward1",
                target = null,
                title = null,
                type = "POINT"
            ),
            Reward(
                content = "SOCIABILITY",
                discountRate = null,
                quantity = 5,
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
        type = "NORMAL",
        writer = "퀘스트 작성자 작성자"
    )
    DefaultQuestContent(
        quest = quest,
        questImage = {
            DefaultQuestImage(
                imageId = quest.imageId,
                contentDescription = quest.missionTitle
            )
        }
    )
}

