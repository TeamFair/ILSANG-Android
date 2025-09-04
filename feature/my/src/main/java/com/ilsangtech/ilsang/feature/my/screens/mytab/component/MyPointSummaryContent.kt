package com.ilsangtech.ilsang.feature.my.screens.mytab.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyPointSummaryUiModel
import java.util.Locale

@Composable
internal fun MyPointSummaryContent(
    modifier: Modifier = Modifier,
    myPointSummary: MyPointSummaryUiModel
) {
    val seasonInfo =
        "시즌${myPointSummary.seasonNumber} 기준" +
                "(${myPointSummary.seasonStartDate}~${myPointSummary.seasonEndDate})"
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "시즌 요약",
            style = heading01,
            color = Color.Black
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (myPointSummary.topMetroAreaName == null
                || myPointSummary.topCommercialAreaName == null
                || myPointSummary.topContributionPoint == null
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_item_empty),
                            tint = Color.Unspecified,
                            contentDescription = null
                        )
                        Text(
                            text = "기록을 만들어 볼까요?",
                            style = heading01,
                            color = gray500,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "퀘스트를 수행하면, 내 일상존을\n활성화할 수 있어요",
                            style = tapRegularTextStyle,
                            color = gray400,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                MyPointSummaryItemCard(
                    iconRes = R.drawable.icon_metro_reward,
                    heading = "${myPointSummary.nickName}님의 퀘스트 최다 달성 지역은?",
                    title = myPointSummary.topMetroAreaName,
                    seasonInfo = seasonInfo
                )
                MyPointSummaryItemCard(
                    iconRes = R.drawable.icon_commercial_reward,
                    heading = "${myPointSummary.nickName}님의 퀘스트 최다 달성 일상존은?",
                    title = myPointSummary.topCommercialAreaName,
                    seasonInfo = seasonInfo
                )
                MyPointSummaryItemCard(
                    iconRes = R.drawable.icon_contribute_reward,
                    heading = "${myPointSummary.nickName}님의 가장 높은 기여도 점수는?",
                    title = String.format(
                        Locale.getDefault(),
                        "%,d",
                        myPointSummary.topContributionPoint
                    ) + "P",
                    seasonInfo = seasonInfo
                )
            }
        }
    }
}

@Composable
private fun MyPointSummaryItemCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    heading: String,
    title: String,
    seasonInfo: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = heading,
                    style = tapRegularTextStyle,
                    color = gray500
                )
                Text(
                    text = title,
                    style = title01,
                    color = primary500
                )
                Text(
                    text = seasonInfo,
                    style = caption02,
                    color = gray400
                )
            }
            Icon(
                modifier = Modifier.size(67.dp),
                painter = painterResource(iconRes),
                contentDescription = "아이콘",
                tint = Color.Unspecified
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPointSummaryContentPreview() {
    MyPointSummaryContent(
        myPointSummary = MyPointSummaryUiModel(
            nickName = "일상러버",
            topCommercialAreaName = "강남역",
            topMetroAreaName = "강남구",
            topContributionPoint = 1000,
            seasonNumber = 1,
            seasonStartDate = "2023.01.01",
            seasonEndDate = "2023.03.31"
        )
    )
}