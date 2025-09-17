package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.feature.ranking.model.SeasonRewardTitleUiModel

@Composable
internal fun SeasonRewardTitleCard(
    modifier: Modifier = Modifier,
    seasonRewardTitle: SeasonRewardTitleUiModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            when (seasonRewardTitle) {
                is SeasonRewardTitleUiModel.TopRewardTitle -> {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(2.dp),
                        painter = painterResource(
                            when (seasonRewardTitle.rank) {
                                1 -> R.drawable.rank_gold
                                2 -> R.drawable.rank_silver
                                else -> R.drawable.rank_bronze
                            }
                        ),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }

                is SeasonRewardTitleUiModel.OtherRewardTitle -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ProvideTextStyle(heading02.copy(color = gray500)) {
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                                text = "${seasonRewardTitle.rankRange.start}"
                            )
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                                text = "~",
                            )
                            Text(
                                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                                text = "${seasonRewardTitle.rankRange.endInclusive}"
                            )
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                TitleGradeIcon(
                    modifier = Modifier.size(20.dp),
                    titleGrade = seasonRewardTitle.title.grade
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = seasonRewardTitle.title.name,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        lineHeight = 20.sp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun SeasonRewardTitleCardTopRewardPreview() {
    SeasonRewardTitleCard(
        seasonRewardTitle = SeasonRewardTitleUiModel.TopRewardTitle(
            id = "1",
            rank = 1,
            title = Title(
                name = "최고의 일상러",
                type = TitleType.Metro,
                grade = TitleGrade.Legend
            )
        )
    )
}

@Preview
@Composable
private fun SeasonRewardTitleCardOtherRewardPreview() {
    SeasonRewardTitleCard(
        seasonRewardTitle = SeasonRewardTitleUiModel.OtherRewardTitle(
            id = "2",
            rankRange = 4..10,
            title = Title(
                name = "일상 탐험가",
                type = TitleType.Metro,
                grade = TitleGrade.Rare
            )
        )
    )
}
