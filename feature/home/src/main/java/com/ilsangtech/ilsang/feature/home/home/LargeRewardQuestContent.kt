package com.ilsangtech.ilsang.feature.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun LargeRewardQuestsContent(largeRewardQuests: Map<String, List<Quest>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val tabList = remember { listOf("체력", "지능", "매력", "재미", "사회성") }
        val rewardTypeList =
            remember { listOf("STRENGTH", "INTELLECT", "FUN", "SOCIABILITY", "CHARM") }
        val questTapButtonInteractionSource = remember { MutableInteractionSource() }
        Text(
            text = "큰 보상 퀘스트",
            style = largeRewardQuestsContentTitleStyle
        )
        Spacer(Modifier.height(12.dp))
        TabRow(
            containerColor = Color.Transparent,
            contentColor = gray500,
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        width = 40.dp,
                        height = 3.dp,
                        color = primary,
                        shape = RectangleShape
                    )
                }
            },
            divider = {}
        ) {
            tabList.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .height(30.sp.value.dp),
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    content = {
                        Text(
                            text = title,
                            style = largeRewardQuestTapStyle
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = gray300
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        largeRewardQuests[rewardTypeList[selectedTabIndex]]?.forEach {
            LargeRewardQuestCard(
                modifier = Modifier.padding(bottom = 12.dp),
                quest = it
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    onClick = {},
                    interactionSource = questTapButtonInteractionSource,
                    indication = null
                ),
        ) {
            Row {
                Text(
                    text = "전체보기",
                    style = bodyTextStyle,
                    color = gray500
                )
                Icon(
                    painter = painterResource(R.drawable.large_reward_quest_content_right_icon),
                    tint = gray500,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun LargeRewardQuestCard(
    modifier: Modifier = Modifier,
    quest: Quest
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
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
                LargeRewardQuestBadge(
                    modifier = Modifier
                        .zIndex(2f)
                        .offset(x = 10.dp, y = (-10).dp)
                        .align(Alignment.TopEnd),
                    score = quest.score
                )
                AsyncImage(
                    modifier = Modifier
                        .zIndex(1f)
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(
                            color = largeRewardQuestWriterBackgroundColor,
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
                    style = largeRewardQuestTitleStyle,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = quest.writer,
                    maxLines = 1,
                    style = largeRewardQuestWriterStyle,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                RewardChips(quest.rewardNetworkModelList)
            }
            Spacer(Modifier.weight(1f))
            FilledIconButton(
                modifier = Modifier
                    .size(26.dp)
                    .align(Alignment.CenterVertically),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0x1A929292),
                ),
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.large_reward_quest_right_icon),
                    tint = gray500,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun LargeRewardQuestBadge(
    modifier: Modifier = Modifier,
    score: Int
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .background(
                color = primary,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = "${score}XP",
            style = badge02TextStyle,
            color = Color.White
        )
    }
}

@Composable
fun RewardChips(rewardList: List<Reward>) {
    var storedList = emptyList<Reward>()
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rewardList.forEach {
            if (storedList.size < 3) {
                storedList = storedList + it
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    storedList.forEach { reward ->
                        RewardChip(reward)
                    }
                    storedList = emptyList()
                }
            }
        }
        if (storedList.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                storedList.forEach { reward ->
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
            .size(
                width = 50.dp,
                height = 25.dp
            )
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

private val largeRewardQuestWriterBackgroundColor = Color(0xFFF1F5FF)

private val largeRewardQuestsContentTitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)

private val largeRewardQuestTapStyle = TextStyle(
    fontSize = 14.sp,
    lineHeight = 18.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)

private val largeRewardQuestTitleStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 20.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)

private val largeRewardQuestWriterStyle = TextStyle(
    fontSize = 11.sp,
    lineHeight = 16.sp,
    fontFamily = FontFamily(Font(pretendard_regular)),
    color = gray400
)


@Preview(showBackground = true, device = "id:small_phone")
@Composable
fun LargeRewardQuestsContentPreview() {
    LargeRewardQuestsContent(mapOf())
}

