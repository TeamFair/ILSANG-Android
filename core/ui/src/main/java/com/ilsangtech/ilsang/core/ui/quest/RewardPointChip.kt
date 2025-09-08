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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
private fun RewardPointChip(rewardPoint: RewardPoint) {
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
        }
    }
}

@Composable
internal fun RewardPointChips(rewardPointList: List<RewardPoint>) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        rewardPointList.forEach { rewardPoint ->
            RewardPointChip(rewardPoint)
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
    RewardPointChips(rewardPointList)
}
