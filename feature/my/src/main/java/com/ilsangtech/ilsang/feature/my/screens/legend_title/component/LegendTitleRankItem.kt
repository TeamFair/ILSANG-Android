package com.ilsangtech.ilsang.feature.my.screens.legend_title.component

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.title.TitleGradeIcon
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.caption01
import com.ilsangtech.ilsang.designsystem.theme.gray400
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.my.BuildConfig
import com.ilsangtech.ilsang.feature.my.screens.legend_title.model.LegendTitleRankUiModel
import com.ilsangtech.ilsang.designsystem.R as DesignSystemR

@Composable
internal fun LegendTitleRankItem(
    modifier: Modifier = Modifier,
    legendTitleRank: LegendTitleRankUiModel
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (legendTitleRank.rank <= 3) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(2.dp),
                    painter = painterResource(
                        when (legendTitleRank.rank) {
                            1 -> DesignSystemR.drawable.rank_gold
                            2 -> DesignSystemR.drawable.rank_silver
                            else -> DesignSystemR.drawable.rank_bronze
                        }
                    ),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
            } else {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text = legendTitleRank.rank.toString(),
                    style = heading02.copy(
                        fontSize = 15.dp.toSp(),
                        lineHeight = 18.dp.toSp()
                    )
                )
            }
            Spacer(Modifier.width(8.dp))

            if (legendTitleRank.profileImageId == null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F5FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "\uD83D\uDE0D",
                        style = title01.copy(
                            fontSize = 23.dp.toSp(),
                            lineHeight = 24.dp.toSp()
                        )
                    )
                }
            } else {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    model = BuildConfig.IMAGE_URL + legendTitleRank.profileImageId,
                    contentDescription = null
                )
            }

            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = legendTitleRank.nickname,
                    style = heading02
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TitleGradeIcon(
                        modifier = modifier.size(12.dp),
                        titleGrade = legendTitleRank.title.grade
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = legendTitleRank.title.name,
                        style = badge02TextStyle,
                        color = gray400
                    )
                }
                Text(
                    text = legendTitleRank.createdAt + " 획득",
                    style = caption01,
                    color = gray400
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(primary)
                    .height(20.dp)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LV." + legendTitleRank.level,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.dp.toSp(),
                    ),
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun LegendTitleRankItemPreview() {
    val legendTitleRank = LegendTitleRankUiModel(
        rank = 4,
        nickname = "일상마스터",
        profileImageId = null,
        level = 10,
        title = Title(
            name = "모두의 시선을 받는 자",
            grade = TitleGrade.Legend,
            type = TitleType.Contribution
        ),
        createdAt = "2025.10.27"
    )
    LegendTitleRankItem(legendTitleRank = legendTitleRank)
}