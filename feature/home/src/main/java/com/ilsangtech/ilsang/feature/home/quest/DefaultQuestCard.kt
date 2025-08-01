package com.ilsangtech.ilsang.feature.home.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.ui.quest.QuestBottomSheet
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun DefaultQuestCard(
    modifier: Modifier = Modifier,
    quest: Quest,
    badge: @Composable (Modifier) -> Unit,
    onApproveButtonClick: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        QuestBottomSheet(
            quest = quest,
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            onApproveButtonClick = onApproveButtonClick
        )
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = { showBottomSheet = true }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 23.dp,
                    vertical = 20.dp
                )
        ) {
            Box(
                modifier = Modifier.padding(
                    top = 10.dp,
                    end = 10.dp
                )
            ) {
                badge(
                    modifier
                        .align(Alignment.TopEnd)
                        .zIndex(2f)
                        .offset(x = 10.dp, y = (-10).dp)
                )
                AsyncImage(
                    modifier = Modifier
                        .zIndex(1f)
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            color = defaultQuestCardWriterBackgroundColor,
                        ),
                    model = BuildConfig.IMAGE_URL + quest.imageId,
                    contentDescription = quest.missionTitle
                )
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    text = quest.missionTitle,
                    maxLines = 1,
                    style = defaultQuestCardTitleStyle,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = quest.writer,
                    maxLines = 1,
                    style = defaultQuestCardWriterStyle,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                RewardChips(quest.rewardList)
            }
            Spacer(Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(R.drawable.large_reward_quest_right_icon),
                tint = gray500,
                contentDescription = null
            )
        }
    }
}

@Composable
fun RewardChips(rewardList: List<Reward>) {
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
fun RewardChip(reward: Reward) {
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

private val defaultQuestCardWriterBackgroundColor = Color(0xFFF1F5FF)

private val defaultQuestCardTitleStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 20.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)

private val defaultQuestCardWriterStyle = TextStyle(
    fontSize = 11.sp,
    lineHeight = 16.sp,
    fontFamily = FontFamily(Font(pretendard_regular)),
    color = gray400
)
