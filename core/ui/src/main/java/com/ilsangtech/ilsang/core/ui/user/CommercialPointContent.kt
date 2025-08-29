package com.ilsangtech.ilsang.core.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.ilsangtech.ilsang.core.ui.user.model.TopCommercialAreaUiModel
import com.ilsangtech.ilsang.core.ui.user.model.TotalOwnerContributionUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.designsystem.theme.tapBoldTextStyle
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.title02
import com.ilsangtech.ilsang.designsystem.theme.toSp
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun TopCommercialAreaContent(
    modifier: Modifier = Modifier,
    topCommercialArea: TopCommercialAreaUiModel
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "일상존 점수",
            style = heading02
        )

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                modifier = Modifier.size(67.dp),
                painter = painterResource(R.drawable.icon_commercial_reward),
                tint = Color.Unspecified,
                contentDescription = null
            )
            Column {
                Text(
                    text = topCommercialArea.commercialAreaName,
                    style = title02
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = String.format(
                        Locale.getDefault(),
                        "%,d",
                        topCommercialArea.point
                    ) + "P",
                    style = title01,
                    color = primary500
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "${topCommercialArea.commercialAreaName} 전체 기여자 중 " +
                            "${topCommercialArea.contributionPercent}%" +
                            "를 기여했어요!",
                    style = caption02,
                    color = gray400
                )
            }
        }
    }
}

@Composable
fun TotalOwnerContributionContent(
    modifier: Modifier = Modifier,
    totalOwnerContributions: List<TotalOwnerContributionUiModel>
) {
    Column(modifier = modifier) {
        Text(
            text = "많이 기여한 일상존",
            style = heading02,
            color = Color.Black
        )
        Spacer(Modifier.height(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
            totalOwnerContributions.take(3)
                .forEachIndexed { index, totalOwnerContribution ->
                    TotalOwnerContributionItem(
                        rank = index + 1,
                        contributionPointSum = totalOwnerContributions.sumOf { it.point },
                        totalOwnerContribution = totalOwnerContribution
                    )
                }
        }
    }
}

@Composable
private fun TotalOwnerContributionItem(
    rank: Int,
    contributionPointSum: Int,
    totalOwnerContribution: TotalOwnerContributionUiModel
) {
    val (rankColor, rankBorderColor) = when (rank) {
        1 -> Color(0xFFFFC952) to Color(0xFFFF8400)
        2 -> gray100 to gray300
        else -> Color(0xFFAA6600) to Color(0xFF9C5000)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(rankColor)
                        .border(
                            width = 1.dp,
                            color = rankBorderColor,
                            shape = CircleShape
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = rank.toString(),
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.dp.toSp()
                        ),
                        color = Color.White
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = totalOwnerContribution.commercialAreaName,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.dp.toSp(),
                        lineHeight = 20.dp.toSp()
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${
                        ((totalOwnerContribution.point.toDouble())
                                / contributionPointSum.toDouble() * 100).roundToInt()
                    }%",
                    style = tapBoldTextStyle,
                    color = primary
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = String.format(
                        Locale.getDefault(),
                        "%,d",
                        totalOwnerContribution.point
                    ) + "P",
                    style = caption02,
                    color = gray400
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(CircleShape)
                .background(gray100),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(
                        ((totalOwnerContribution.point.toDouble())
                                / contributionPointSum.toDouble()).toFloat()
                    )
                    .height(12.dp)
                    .clip(CircleShape)
                    .background(primary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserCommercialPointCardPreview() {
    val userCommercialPointUiModel = UserCommercialPointUiModel(
        nickname = "박일상 Daily Park",
        topCommercialArea = TopCommercialAreaUiModel(
            commercialAreaName = "강남역",
            contributionPercent = 70,
            point = 700
        ),
        totalOwnerContributions = listOf(
            TotalOwnerContributionUiModel(
                commercialAreaName = "강남역",
                point = 700
            ),
            TotalOwnerContributionUiModel(
                commercialAreaName = "역삼역",
                point = 300
            ),
            TotalOwnerContributionUiModel(
                commercialAreaName = "정자역",
                point = 300
            )

        )
    )

    Column {
        TopCommercialAreaContent(
            topCommercialArea = userCommercialPointUiModel.topCommercialArea!!
        )
        Spacer(Modifier.height(48.dp))
        TotalOwnerContributionContent(
            totalOwnerContributions = userCommercialPointUiModel.totalOwnerContributions
        )
    }
}