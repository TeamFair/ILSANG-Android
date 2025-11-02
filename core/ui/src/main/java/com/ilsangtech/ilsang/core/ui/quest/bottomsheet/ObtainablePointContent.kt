package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.ui.R
import com.ilsangtech.ilsang.core.ui.quest.RewardPointIcon
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun ObtainablePointContent(
    modifier: Modifier = Modifier,
    rewardPoints: List<RewardPoint>,
    isIsZoneQuest: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestBottomSheetTextChip(text = "획득 가능 포인트")
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            rewardPoints.forEach { rewardPoint ->
                ObtainablePointItem(
                    rewardPoint = rewardPoint,
                    isIsZoneQuest = isIsZoneQuest
                )
            }
        }
    }
}

@Composable
private fun ObtainablePointItem(
    rewardPoint: RewardPoint,
    isIsZoneQuest: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(9.dp)
                )
                .padding(vertical = 3.2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = when (rewardPoint) {
                    is RewardPoint.Metro -> "일상지역"
                    is RewardPoint.Commercial -> "일상존"
                    is RewardPoint.Contribute -> "기여도"
                },
                style = TextStyle(
                    fontFamily = pretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.dp.toSp(),
                    lineHeight = 14.4.dp.toSp(),
                    letterSpacing = (-0.32).dp.toSp()
                ),
                color = gray500
            )
        }
        RewardPointIcon(
            modifier = Modifier.size(38.dp),
            rewardPoint = rewardPoint
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${rewardPoint.point}P",
                style = questBottomSheetRewardPointTextStyle
            )
            Icon(
                modifier = Modifier.padding(
                    vertical = 3.dp,
                    horizontal = 4.dp
                ),
                painter = painterResource(R.drawable.quest_bottom_sheet_reward_xp_up),
                tint = Color.Unspecified,
                contentDescription = null
            )
            if (rewardPoint is RewardPoint.Contribute && isIsZoneQuest) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFF7D13),
                                    Color(0xFFFFC525)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "X2",
                        style = badge02TextStyle.copy(
                            fontSize = 10.dp.toSp(),
                            lineHeight = 12.dp.toSp(),
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

private val questBottomSheetRewardPointTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 13.sp,
    lineHeight = 20.sp,
    color = primary
)

@Preview(showBackground = true)
@Composable
private fun ObtainablePointContentPreview() {
    val rewardPoints = listOf(
        RewardPoint.Metro(point = 10),
        RewardPoint.Commercial(point = 20),
        RewardPoint.Contribute(point = 30)
    )
    ObtainablePointContent(
        rewardPoints = rewardPoints,
        isIsZoneQuest = true
    )
}




