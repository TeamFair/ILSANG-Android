package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
private fun RewardPointChip(
    isSmallSize: Boolean = false,
    rewardPoint: RewardPoint,
    isIsZoneQuest: Boolean
) {
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
            modifier = Modifier.padding(
                horizontal =
                    if (isSmallSize) 6.dp else 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RewardPointIcon(
                modifier = Modifier.size(18.dp),
                rewardPoint = rewardPoint
            )
            Text(
                text = "${rewardPoint.point}",
                style = rewardPointChipTextStyle(),
                color = primary
            )
            Spacer(Modifier.width(1.dp))
            Text(
                text = "P",
                style = rewardPointChipTextStyle(),
                color = primary
            )
            if (rewardPoint is RewardPoint.Contribute && isIsZoneQuest) {
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "X2",
                    style = badge02TextStyle.copy(
                        fontSize = 10.dp.toSp(),
                        lineHeight = 12.dp.toSp(),
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFFF8415),
                                Color(0xFFFFB622)
                            )
                        )
                    )
                )
            }
        }
    }
}

@Composable
internal fun RewardPointChips(
    isSmallSize: Boolean = false,
    rewardPointList: List<RewardPoint>,
    isIsZoneQuest: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            if (isSmallSize) 3.dp else 4.dp
        )
    ) {
        rewardPointList.find { it is RewardPoint.Metro }?.let { rewardPoint ->
            RewardPointChip(
                isSmallSize = isSmallSize,
                rewardPoint = rewardPoint,
                isIsZoneQuest = isIsZoneQuest
            )
        }
        rewardPointList.find { it is RewardPoint.Commercial }?.let { rewardPoint ->
            RewardPointChip(
                isSmallSize = isSmallSize,
                rewardPoint = rewardPoint,
                isIsZoneQuest = isIsZoneQuest
            )
        }
        rewardPointList.find { it is RewardPoint.Contribute }?.let { rewardPoint ->
            RewardPointChip(
                isSmallSize = isSmallSize,
                rewardPoint = rewardPoint,
                isIsZoneQuest = isIsZoneQuest
            )
        }
    }
}

@Composable
private fun rewardPointChipTextStyle() = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.dp.toSp(),
    lineHeight = 16.dp.toSp()
)

@Preview(showBackground = true)
@Composable
private fun RewardPointChipsPreview() {
    val rewardPointList = listOf(
        RewardPoint.Metro(2),
        RewardPoint.Commercial(5),
        RewardPoint.Contribute(10)
    )
    RewardPointChips(
        rewardPointList = rewardPointList,
        isIsZoneQuest = false
    )
}
