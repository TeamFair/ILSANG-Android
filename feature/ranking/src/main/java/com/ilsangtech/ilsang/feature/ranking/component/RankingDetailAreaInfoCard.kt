package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.feature.ranking.BuildConfig
import com.ilsangtech.ilsang.feature.ranking.model.AreaRankUiModel
import java.util.Locale

@Composable
internal fun RankingDetailAreaInfoCard(
    modifier: Modifier = Modifier,
    areaRankUiModel: AreaRankUiModel
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { areaRankUiModel.images.size }
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp)
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 12.dp, topEnd = 12.dp
                    )
                ),
            state = pagerState
        ) { page ->
            AsyncImage(
                modifier = Modifier.height(150.dp),
                model = BuildConfig.IMAGE_URL + areaRankUiModel.images[page],
                contentDescription = null
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(areaRankUiModel.images.size) { index ->
                val color = if (pagerState.currentPage == index) {
                    gray500
                } else {
                    gray100
                }

                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = areaRankUiModel.areaName,
                    style = heading01,
                    color = Color.Black
                )
                if (areaRankUiModel.rank <= 3) {
                    Icon(
                        modifier = Modifier
                            .size(22.dp)
                            .padding(
                                vertical = 1.47.dp,
                                horizontal = 1.46.dp
                            ),
                        painter = painterResource(
                            when (areaRankUiModel.rank) {
                                1 -> R.drawable.rank_gold
                                2 -> R.drawable.rank_silver
                                else -> R.drawable.rank_bronze
                            }
                        ),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }
            }
            ProvideTextStyle(caption02.copy(color = gray500)) {
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    Text(text = "실시간 랭킹:")
                    Spacer(Modifier.width(4.dp))
                    Text(text = "${areaRankUiModel.rank}위")
                    Box(
                        modifier = Modifier.width(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        VerticalDivider(
                            modifier = Modifier.padding(vertical = 3.dp),
                            color = gray300
                        )
                    }
                    Text(text = "누적점수:")
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = String.format(
                            Locale.getDefault(),
                            "%,d",
                            areaRankUiModel.point
                        ) + 'p'
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RankingDetailAreaInfoCardPreview() {
    RankingDetailAreaInfoCard(
        areaRankUiModel = AreaRankUiModel(
            areaName = "경기 남부",
            rank = 1,
            point = 123456789,
            images = listOf(
                "https://picsum.photos/200/300",
                "https://picsum.photos/200/300",
                "https://picsum.photos/200/300"
            )
        )
    )
}