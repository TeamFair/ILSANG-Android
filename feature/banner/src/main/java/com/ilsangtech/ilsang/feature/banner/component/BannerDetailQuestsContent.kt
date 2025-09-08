package com.ilsangtech.ilsang.feature.banner.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.quest.CompletedQuestCard
import com.ilsangtech.ilsang.core.ui.quest.QuestCardWithArrow
import com.ilsangtech.ilsang.designsystem.component.DropDownMenu
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.banner.BannerDetailQuestType
import com.ilsangtech.ilsang.feature.banner.BannerDetailSortType
import kotlinx.coroutines.flow.flowOf

internal fun LazyListScope.bannerDetailQuestsContent(
    onGoingQuests: LazyPagingItems<BannerQuest>,
    completedQuests: LazyPagingItems<BannerQuest>,
    selectedQuestType: BannerDetailQuestType,
    selectedSortType: BannerDetailSortType,
    onQuestClick: (BannerQuest) -> Unit,
    onQuestTypeChanged: (BannerDetailQuestType) -> Unit,
    onSortTypeChanged: (BannerDetailSortType) -> Unit
) {
    item {
        TabRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            containerColor = Color.Transparent,
            contentColor = primary,
            selectedTabIndex = BannerDetailQuestType.entries.indexOf(selectedQuestType),
            indicator = { tabPositions ->
                if (BannerDetailQuestType.entries.indexOf(selectedQuestType) < tabPositions.size) {
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(
                            tabPositions[BannerDetailQuestType.entries.indexOf(selectedQuestType)]
                        ),
                        width = 127.dp,
                        height = 3.dp,
                        color = primary
                    )
                }
            },
            divider = { HorizontalDivider(color = gray100) }
        ) {
            BannerDetailQuestType.entries.forEach { bannerDetailQuestType ->
                Tab(
                    selected = selectedQuestType == bannerDetailQuestType,
                    unselectedContentColor = gray300,
                    selectedContentColor = gray500,
                    onClick = { onQuestTypeChanged(bannerDetailQuestType) },
                    text = {
                        Text(
                            text = bannerDetailQuestType.toString(),
                            style = TextStyle(
                                fontFamily = pretendardFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.dp.toSp(),
                                lineHeight = 24.dp.toSp()
                            )
                        )
                    }
                )
            }
        }
    }
    item {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 2.dp
                ),
                text = "특별 퀘스트 모음",
                style = title02
            )
            DropDownMenu(
                list = BannerDetailSortType.entries,
                selectedItem = selectedSortType,
                onItemSelected = onSortTypeChanged,
            )
        }
    }
    item { Spacer(Modifier.height(11.dp)) }

    val bannerQuests =
        if (selectedQuestType == BannerDetailQuestType.OnGoing) {
            onGoingQuests
        } else {
            completedQuests
        }

    if (bannerQuests.loadState.refresh is LoadState.NotLoading
        && bannerQuests.itemCount == 0
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 70.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val (headingText, subText) =
                    if (selectedQuestType == BannerDetailQuestType.OnGoing) {
                        "퀘스트를 모두 완료하셨어요!" to "상상할 수 없는 퀘스트를 준비중이니\n" +
                                "다음 업데이트를 기대해주세요!"
                    } else {
                        "완료된 퀘스트가 없어요" to "퀘스트를 수행하러 가볼까요?"
                    }
                Text(
                    text = headingText,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        lineHeight = 1.5.em
                    ),
                    color = gray500
                )
                Text(
                    text = subText,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 17.sp,
                        lineHeight = 1.5.em
                    ),
                    color = gray400
                )
            }
        }
    }

    items(bannerQuests.itemCount) { index ->
        val bannerQuest = bannerQuests[index]
        bannerQuest?.let {
            Column {
                if (selectedQuestType == BannerDetailQuestType.OnGoing) {
                    QuestCardWithArrow(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        quest = bannerQuest,
                        onClick = { onQuestClick(bannerQuest) }
                    )
                } else {
                    CompletedQuestCard(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        quest = bannerQuest,
                        onClick = { onQuestClick(bannerQuest) }
                    )
                }

                if (bannerQuests.itemCount - 1 != index) {
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerDetailQuestsContentPreview() {
    val onGoingQuests = flowOf(
        PagingData.from(
            listOf(
                BannerQuest(
                    questId = 1,
                    questType = QuestType.Normal,
                    expireDate = "2023-12-31",
                    imageId = "imageId1",
                    mainImageId = "mainImageId1",
                    rewards = emptyList(),
                    title = "OnGoing Quest 1",
                    writerName = "Writer 1"
                ),
                BannerQuest(
                    questId = 2,
                    questType = QuestType.Event,
                    expireDate = "2024-01-15",
                    imageId = "imageId2",
                    mainImageId = "mainImageId2",
                    rewards = emptyList(),
                    title = "OnGoing Quest 2",
                    writerName = "Writer 2"
                )
            )
        )
    ).collectAsLazyPagingItems()

    val completedQuests = flowOf(
        PagingData.from(
            listOf(
                BannerQuest(
                    questId = 3,
                    questType = QuestType.Repeat.Daily,
                    expireDate = "2023-11-30",
                    imageId = "imageId3",
                    mainImageId = "mainImageId3",
                    rewards = emptyList(),
                    title = "Completed Quest 1",
                    writerName = "Writer 3"
                )
            )
        )
    ).collectAsLazyPagingItems()

    LazyColumn {
        bannerDetailQuestsContent(
            onGoingQuests = onGoingQuests,
            completedQuests = completedQuests,
            selectedQuestType = BannerDetailQuestType.OnGoing,
            selectedSortType = BannerDetailSortType.Popular,
            onQuestClick = {},
            onQuestTypeChanged = {},
            onSortTypeChanged = {}
        )
    }
}