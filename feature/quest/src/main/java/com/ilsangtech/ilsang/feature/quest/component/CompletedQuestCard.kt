package com.ilsangtech.ilsang.feature.quest.component

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
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.ui.quest.DefaultQuestCard
import com.ilsangtech.ilsang.core.ui.quest.DefaultQuestContent
import com.ilsangtech.ilsang.core.ui.quest.QuestImageWithBadge
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun CompletedQuestCard(
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
                    horizontal = 13.5.dp,
                    vertical = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DefaultQuestContent(
                modifier = Modifier.weight(1f),
                quest = quest,
                questImage = {
                    QuestImageWithBadge(
                        imageId = quest.imageId,
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
private fun CompletedQuestCardPreview() {
    CompletedQuestCard(
        quest = Quest(
            createDate = "2023-10-27",
            creatorRole = "USER",
            expireDate = "2023-11-27",
            favoriteYn = false,
            imageId = "sample_image_id",
            mainImageId = "sample_main_image_id",
            marketId = "sample_market_id",
            missionId = "sample_mission_id",
            missionTitle = "Sample Mission Title",
            missionType = "ETC",
            popularYn = false,
            questId = "sample_quest_id",
            rewardList = listOf(
                Reward(
                    content = "Sample Reward Content",
                    discountRate = 10,
                    quantity = 1,
                    questId = "sample_quest_id",
                    rewardId = "sample_reward_id",
                    target = "USER",
                    title = "Sample Reward Title",
                    type = "COUPON"
                )
            ),
            score = 100,
            status = "COMPLETED",
            target = "USER",
            type = "NORMAL",
            writer = "Sample Writer"
        ),
        onClick = {}
    )
}