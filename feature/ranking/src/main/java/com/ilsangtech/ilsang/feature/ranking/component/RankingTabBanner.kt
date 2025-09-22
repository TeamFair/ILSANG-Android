package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.util.DateConverter
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.payboocFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.primary500
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel

@Composable
internal fun RankingTabBanner(
    modifier: Modifier = Modifier,
    season: SeasonUiModel.Season,
    onQuestButtonClick: () -> Unit,
    onSeasonRewardButtonClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = primary,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "일상 특별 이벤트 $season",
                    style = TextStyle(
                        fontFamily = payboocFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        lineHeight = 1.3.em,
                        color = Color.White
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = DateConverter.formatDate(season.startDate) +
                            "~" + DateConverter.formatDate(season.endDate),
                    style = TextStyle(
                        fontFamily = payboocFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        lineHeight = 1.3.em,
                        color = Color(0xFFE5D3FB)
                    )
                )
                Spacer(Modifier.height(13.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary500,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(36.dp),
                        contentPadding = PaddingValues(
                            top = 8.dp, bottom = 8.dp,
                            start = 12.dp, end = 8.dp
                        ),
                        onClick = onQuestButtonClick
                    ) {
                        Text(
                            text = "퀘스트 수행하기",
                            style = caption02,
                            color = Color.White
                        )
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            painter = painterResource(R.drawable.icon_right),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primary500,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(36.dp),
                        contentPadding = PaddingValues(
                            top = 8.dp, bottom = 8.dp,
                            start = 12.dp, end = 8.dp
                        ),
                        onClick = onSeasonRewardButtonClick
                    ) {
                        Text(
                            text = "시즌 보상",
                            style = caption02,
                            color = Color.White
                        )
                        Icon(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(16.dp),
                            painter = painterResource(R.drawable.icon_right),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.icon_reward_box),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
internal fun RankingTabBannerPreview() {
    val season = SeasonUiModel.Season(
        seasonId = 1,
        seasonNumber = 1,
        startDate = "2025-01-01T00:00:00",
        endDate = "2025-12-31T23:59:59"
    )
    RankingTabBanner(
        season = season,
        onQuestButtonClick = {},
        onSeasonRewardButtonClick = {}
    )
}