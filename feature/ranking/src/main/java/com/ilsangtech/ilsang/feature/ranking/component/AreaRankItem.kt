package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.title01
import com.ilsangtech.ilsang.designsystem.theme.toSp
import java.util.Locale

@Composable
internal fun AreaRankItem(
    modifier: Modifier = Modifier,
    areaName: String,
    rank: Int,
    point: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 27.dp)
                .padding(start = 36.dp, end = 23.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(30.dp),
                contentAlignment = Alignment.Center
            ) {
                if (rank <= 3) {
                    Icon(
                        modifier = Modifier.padding(2.dp),
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
                } else {
                    Text(
                        text = rank.toString(),
                        style = heading02.copy(
                            fontSize = 15.dp.toSp(),
                            lineHeight = 18.dp.toSp()
                        ),
                        color = gray500
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = areaName,
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        lineHeight = 20.sp
                    ),
                    color = gray500
                )
                Text(
                    text = String.format(
                        locale = Locale.getDefault(),
                        format = "%,d",
                        point
                    ) + 'p',
                    style = title01,
                    color = Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Color(0x1A929292)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_right),
                    contentDescription = null,
                    tint = gray500
                )
            }
        }
    }
}

@Preview
@Composable
private fun AreaRankItemPreview() {
    AreaRankItem(
        modifier = Modifier,
        areaName = "성남시청 일대",
        rank = 1,
        point = 81223840,
        onClick = {}
    )
}
