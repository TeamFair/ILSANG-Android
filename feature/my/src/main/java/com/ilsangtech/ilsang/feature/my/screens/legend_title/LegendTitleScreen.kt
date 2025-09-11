package com.ilsangtech.ilsang.feature.my.screens.legend_title

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.my.screens.legend_title.component.LegendTitleRankItem
import com.ilsangtech.ilsang.feature.my.screens.legend_title.component.LegendTitleScreenHeader
import com.ilsangtech.ilsang.feature.my.screens.legend_title.model.LegendTitleRankUiModel
import kotlinx.coroutines.flow.flowOf

@Composable
private fun LegendTitleScreen(
    titleName: String,
    legendTitleRankPagingItems: LazyPagingItems<LegendTitleRankUiModel>,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LegendTitleScreenHeader(
                title = titleName,
                onBackButtonClick = onBackButtonClick
            )

            if (legendTitleRankPagingItems.loadState.refresh is LoadState.NotLoading
                && legendTitleRankPagingItems.itemCount == 0
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "해당 칭호를 획득한\n유저가 없어요",
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp,
                            lineHeight = 33.sp
                        ),
                        color = gray300,
                        textAlign = TextAlign.Center
                    )
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = 16.dp, bottom = 72.dp,
                    start = 20.dp, end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(legendTitleRankPagingItems.itemCount) {
                    val item = legendTitleRankPagingItems[it]
                    item?.let { uiModel ->
                        LegendTitleRankItem(legendTitleRank = uiModel)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LegendTitleScreenPreview() {
    val legendTitleRankPagingItems = flowOf(
        PagingData.from(
            listOf(
                LegendTitleRankUiModel(
                    rank = 1,
                    nickname = "익명의 사용자",
                    profileImageId = null,
                    level = 10,
                    title = Title(
                        name = "강남구 보안관",
                        grade = TitleGrade.Legend,
                        type = TitleType.Metro
                    ),
                    createdAt = "2023.10.27"
                ),
                LegendTitleRankUiModel(
                    rank = 2,
                    nickname = "고독한 미식가",
                    profileImageId = "some_image_id",
                    level = 25,
                    title = Title(
                        name = "맛집 개척자",
                        grade = TitleGrade.Legend,
                        type = TitleType.Commercial
                    ),
                    createdAt = "2023.11.15"
                )
            )
        )
    ).collectAsLazyPagingItems()

    LegendTitleScreen(
        titleName = "강남구 보안관",
        legendTitleRankPagingItems = legendTitleRankPagingItems,
        onBackButtonClick = {})
}