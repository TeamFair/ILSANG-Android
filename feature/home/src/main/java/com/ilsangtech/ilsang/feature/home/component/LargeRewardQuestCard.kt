package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.ui.quest.DefaultQuestCard
import com.ilsangtech.ilsang.core.ui.quest.DefaultQuestContent
import com.ilsangtech.ilsang.core.ui.quest.DefaultQuestImage
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.R

@Composable
internal fun LargeRewardQuestCard(
    modifier: Modifier = Modifier,
    quest: Quest,
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
                    horizontal = 23.dp,
                    vertical = 20.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultQuestContent(
                modifier = modifier,
                quest = quest,
                questImage = {
                    DefaultQuestImage(
                        imageId = quest.imageId,
                        contentDescription = "퀘스트 이미지"
                    )
                }
            )
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.CenterVertically)
                    .clip(CircleShape)
                    .background(Color(0xFFF4F4F4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(5.dp),
                    painter = painterResource(R.drawable.large_reward_quest_right_icon),
                    tint = gray500,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun LargeRewardQuestCardPreview() {
    LargeRewardQuestCard(
        quest = Quest(
            createDate = "2023-10-26",
            creatorRole = "USER",
            expireDate = "2023-11-26",
            favoriteYn = false,
            imageId = null,
            mainImageId = null,
            marketId = null,
            missionId = "mission123",
            missionTitle = "일상속 소소한 행동",
            missionType = "DAILY",
            popularYn = false,
            questId = "quest123",
            rewardList = listOf(
                Reward(
                    content = "커피",
                    discountRate = null,
                    quantity = 1,
                    questId = "quest123",
                    rewardId = "reward123",
                    target = null,
                    title = "스타벅스 커피",
                    type = "COFFEE"
                )
            ),
            score = 10,
            status = "ACTIVE",
            target = "ALL",
            type = "NORMAL",
            writer = "ilsang"
        ),
        onClick = {}
    )
}
