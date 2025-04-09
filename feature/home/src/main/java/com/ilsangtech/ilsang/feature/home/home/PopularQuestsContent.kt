package com.ilsangtech.ilsang.feature.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_regular
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.feature.home.BuildConfig


@Composable
fun PopularQuestCard(
    modifier: Modifier = Modifier,
    quest: Quest
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {
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

fun LazyListScope.popularQuestsContent(popularQuests: List<Quest>) {
    item {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = "이번달 인기 퀘스트 모음",
            style = TextStyle(
                fontSize = 19.sp,
                lineHeight = 22.sp,
                fontFamily = FontFamily(Font(pretendard_semibold)),
            )
        )
    }
    item {
        Spacer(Modifier.height(12.dp))
    }
    item {
        val pageCount = popularQuests.size / 4
        val pagerState = rememberPagerState { pageCount }

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
                            quest = questSubList[index]
                        )
                        Spacer(Modifier.width(8.dp))
                        PopularQuestCard(
                            modifier = Modifier.weight(1f),
                            quest = questSubList[index + 1]
                        )
                    }
                    if (index != questSubList.size - 1) {
                        Spacer(Modifier.height(8.dp))
                    }
                }
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