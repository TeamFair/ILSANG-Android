package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun MyQuestRank(
    modifier: Modifier = Modifier,
    rank: Int?
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp)
            .padding(top = 16.dp, bottom = 17.dp)
    ) {
        QuestBottomSheetTextChip(text = "나의 랭킹")
        if (rank == null) {
            Text(
                modifier = Modifier.padding(top = 82.dp),
                text = "-",
                style = myQuestRankTextStyle(),
                color = primary
            )
        } else {
            Row(
                modifier = Modifier.padding(top = 34.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (rank <= 3) {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(2.4.dp),
                        painter = painterResource(
                            when (rank) {
                                1 -> R.drawable.rank_gold
                                2 -> R.drawable.rank_silver
                                else -> R.drawable.rank_bronze
                            }
                        ),
                        tint = Color.Unspecified,
                        contentDescription = null
                    )
                }
                Text(
                    text = "${rank}위",
                    style = myQuestRankTextStyle(),
                    color = primary
                )
            }
        }
    }
}

@Composable
private fun myQuestRankTextStyle() = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.dp.toSp(),
    lineHeight = 34.dp.toSp()
)

@Preview(showBackground = true)
@Composable
private fun MyQuestRankPreview() {
    MyQuestRank(rank = 4)
}