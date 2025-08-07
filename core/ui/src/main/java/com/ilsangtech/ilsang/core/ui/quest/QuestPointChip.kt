package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.ui.R
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun QuestPointChip(reward: Reward) {
    Box(
        modifier = Modifier
            .height(25.dp)
            .border(
                width = 1.dp,
                color = primary,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(rewardTypeToRewardPainter[reward.content]!!),
                tint = Color.Unspecified,
                contentDescription = null
            )

            Text(
                text = "${reward.quantity}",
                style = pointChipTextStyle(),
                color = primary
            )
            Spacer(Modifier.width(1.dp))
            Text(
                text = "P",
                style = pointChipTextStyle(),
                color = primary
            )
        }
    }
}

@Composable
fun QuestPointChipList(rewardList: List<Reward>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rewardList
            .chunked(3)
            .forEach { chunk ->
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    chunk.forEach { reward ->
                        QuestPointChip(reward)
                    }
                }
            }
    }
}

@Composable
private fun pointChipTextStyle() = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.dp.toSp(),
    lineHeight = 16.dp.toSp()
)

private val rewardTypeToRewardPainter = mapOf(
    "INTELLECT" to R.drawable.bulb,
    "SOCIABILITY" to R.drawable.social,
    "STRENGTH" to R.drawable.strength,
    "FUN" to R.drawable.`fun`,
    "CHARM" to R.drawable.heart
)