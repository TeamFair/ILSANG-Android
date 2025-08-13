package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.ui.quest.QuestCardWithArrow
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.R
import kotlinx.coroutines.launch

@Composable
internal fun LargeRewardQuestContent(
    modifier: Modifier = Modifier,
    largeRewardQuests: List<LargeRewardQuest>,
    onQuestClick: (questId: Int) -> Unit,
    onMoreButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "큰 보상 퀘스트",
            style = largeRewardQuestsContentTitleStyle
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            largeRewardQuests.forEach { quest ->
                QuestCardWithArrow(
                    quest = quest,
                    onClick = { onQuestClick(quest.questId) }
                )
            }
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = 1.dp,
                color = gray100
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = gray500
            ),
            shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = onMoreButtonClick
        ) {
            Text(
                text = "더 많은 퀘스트 보기",
                style = bodyTextStyle
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(com.ilsangtech.ilsang.designsystem.R.drawable.icon_right),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LargeRewardQuestContentPreview() {
    val largeRewardQuests = listOf(
        LargeRewardQuest(
            questId = 1,
            expireDate = "2023-12-31",
            imageId = "sample_image_1",
            mainImageId = "sample_main_image_1",
            rewards = listOf(
                RewardPoint.Metro(5),
                RewardPoint.Commerical(10),
                RewardPoint.Contribute(15)
            ),
            title = "Sample Quest 1",
            writerName = "Writer 1"
        ),
        LargeRewardQuest(
            questId = 2,
            expireDate = "2024-01-15",
            imageId = "sample_image_2",
            mainImageId = "sample_main_image_2",
            rewards = listOf(
                RewardPoint.Metro(3),
                RewardPoint.Commerical(8),
                RewardPoint.Contribute(12)
            ),
            title = "Sample Quest 2",
            writerName = "Writer 2"
        ),
        LargeRewardQuest(
            questId = 3,
            expireDate = "2024-01-15",
            imageId = "sample_image_3",
            mainImageId = "sample_main_image_3",
            rewards = listOf(
                RewardPoint.Metro(3),
                RewardPoint.Commerical(8),
                RewardPoint.Contribute(12)
            ),
            title = "Sample Quest 2",
            writerName = "Writer 2"
        )
    )
    LargeRewardQuestContent(
        largeRewardQuests = largeRewardQuests,
        onQuestClick = {},
        onMoreButtonClick = {}
    )
}

@Composable
internal fun LargeRewardQuestsContent(
    largeRewardQuests: Map<String, List<Quest>>,
    navigateToQuestTab: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        val tabList = remember { RewardType.entries.map { it.title } }
        val rewardTypeList = remember { RewardType.entries }

        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState { tabList.size }

        Text(
            text = "큰 보상 퀘스트",
            style = largeRewardQuestsContentTitleStyle
        )
        Spacer(Modifier.height(12.dp))
        TabRow(
            containerColor = Color.Transparent,
            contentColor = gray500,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
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
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
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
        HorizontalPager(
            state = pagerState
        ) {
            Column {
                largeRewardQuests[rewardTypeList[pagerState.currentPage].name]?.forEach {
                    LargeRewardQuestCard(
                        modifier = Modifier.padding(bottom = 12.dp),
                        quest = it,
                        onClick = navigateToQuestTab
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    onClick = navigateToQuestTab,
                    interactionSource = null,
                    indication = null
                )
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


@Preview(showBackground = true, device = "id:small_phone")
@Composable
private fun LargeRewardQuestsContentPreview() {
    LargeRewardQuestsContent(
        largeRewardQuests = mapOf(),
        navigateToQuestTab = {}
    )
}

