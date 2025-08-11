package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.ui.quest.LargeRewardQuestBadge
import com.ilsangtech.ilsang.core.ui.quest.RepeatQuestTypeBadge
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.home.BuildConfig

@Composable
fun PopularQuestCard(
    modifier: Modifier = Modifier,
    quest: Quest,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = onCardClick
    ) {
        Box {
            Box(
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                if (quest.type == QuestType.REPEAT.name) {
                    RepeatQuestTypeBadge(
                        repeatType = QuestType.REPEAT.title
                    )
                } else {
                    LargeRewardQuestBadge(
                        xpSum = quest.rewardList.sumOf { it.quantity }
                    )
                }
            }

            Column {
                AsyncImage(
                    modifier = Modifier.height(160.dp),
                    model = BuildConfig.IMAGE_URL + quest.mainImageId,
                    contentScale = ContentScale.Crop,
                    contentDescription = quest.missionTitle
                )
                Spacer(Modifier.height(9.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .height(
                            popularQuestCardTitleStyle.lineHeight.value.dp * 2
                                    + popularQuestCardWriterStyle.lineHeight.value.dp
                        )
                ) {
                    Text(
                        text = quest.missionTitle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = popularQuestCardTitleStyle
                    )
                    Text(
                        text = quest.writer,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = popularQuestCardWriterStyle
                    )
                }
            }
        }
    }
}

@Composable
fun PopularQuestsContent(
    popularQuests: List<Quest>,
    onPopularQuestClick: (Quest) -> Unit
) {
    val pageCount = popularQuests.size / 4
    val pagerState = rememberPagerState { pageCount }

    Column {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "이번달 인기 퀘스트 모음",
            style = TextStyle(
                fontSize = 19.sp,
                lineHeight = 22.sp,
                fontFamily = FontFamily(Font(pretendard_semibold)),
            )
        )
        Spacer(Modifier.height(12.dp))
        HorizontalPager(
            state = pagerState
        ) {
            val questSubList =
                popularQuests.subList(it * 4, minOf((it + 1) * 4, popularQuests.size))
            Column {
                repeat(questSubList.size / 2) { index ->
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        PopularQuestCard(
                            modifier = Modifier.weight(1f),
                            quest = questSubList[index],
                            onCardClick = { onPopularQuestClick(questSubList[index]) }
                        )
                        Spacer(Modifier.width(8.dp))
                        PopularQuestCard(
                            modifier = Modifier.weight(1f),
                            quest = questSubList[index + 1],
                            onCardClick = { onPopularQuestClick(questSubList[index + 1]) }
                        )
                    }
                    if (index != questSubList.size - 1) {
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(pageCount) {
                val color = if (pagerState.currentPage == it) {
                    gray500
                } else {
                    gray100
                }
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = color,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

private val popularQuestCardTitleStyle = TextStyle(
    fontSize = 15.sp,
    lineHeight = 18.sp,
    fontFamily = FontFamily(
        Font(pretendard_semibold)
    ),
    lineBreak = LineBreak.Heading
)

private val popularQuestCardWriterStyle = TextStyle(
    fontSize = 11.sp,
    lineHeight = 16.sp,
    fontFamily = FontFamily(
        Font(pretendard_regular)
    ),
    color = gray400
)