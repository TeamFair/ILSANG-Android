package com.ilsangtech.ilsang.feature.submit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.ui.quest.RewardPointIcon
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun SubmitSuccessDialog(
    modifier: Modifier = Modifier,
    rewardPoints: List<RewardPoint>,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubmitSuccessDialogIcon()
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "${rewardPoints.sumOf { it.point }}P가 상승했어요",
                    style = submitSuccessDialogTitleStyle()
                )
                Spacer(Modifier.height(12.dp))
                SubmitRewardPointsRow(
                    modifier = Modifier.width(228.dp),
                    rewardPoints = rewardPoints
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    modifier = Modifier.width(228.dp),
                    onClick = onDismissRequest,
                    contentPadding = PaddingValues(vertical = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primary)
                ) {
                    Text(
                        text = "확인",
                        style = buttonTextStyle
                    )
                }
            }
        }
    }
}

@Composable
private fun SubmitRewardPointsRow(
    modifier: Modifier = Modifier,
    rewardPoints: List<RewardPoint>
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rewardPoints.forEach { rewardPoint ->
                SubmitRewardPointItem(rewardPoint)
            }
        }
    }
}

@Composable
private fun SubmitRewardPointItem(rewardPoint: RewardPoint) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RewardPointIcon(
            modifier = Modifier.size(25.dp),
            rewardPoint = rewardPoint
        )
        Text(
            text = "${rewardPoint.point}P",
            style = submitSuccessStatTextStyle()
        )
        Spacer(Modifier.width(2.dp))
        Icon(
            painter = painterResource(R.drawable.icon_reward_point_up),
            tint = Color.Unspecified,
            contentDescription = null
        )
    }
}

@Composable
fun SubmitSuccessDialogIcon() {
    Box(
        modifier = Modifier.size(
            width = 58.dp,
            height = 45.dp
        )
    ) {
        Icon(
            modifier = Modifier
                .width(40.dp)
                .align(Alignment.BottomStart)
                .offset(x = 7.dp),
            painter = painterResource(id = R.drawable.submit_success_icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 2.dp, y = 12.dp)
                .size(3.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFC952))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = (-2).dp)
                .size(4.dp)
                .clip(RectangleShape)
                .background(Color(0xFF99DAF2))
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-13).dp)
                .size(3.dp)
                .clip(RectangleShape)
                .background(
                    Color(0xFFFF8400)
                )
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(3.dp)
                .clip(CircleShape)
                .background(Color(0xFF99DAF2))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-7).dp, y = (-3).dp)
                .size(3.dp)
                .clip(CircleShape)
                .background(Color(0xFFFF8400))
        )
    }
}

@Composable
private fun submitSuccessDialogTitleStyle() = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.dp.toSp(),
    lineHeight = 18.dp.toSp(),
    color = gray500
)

@Composable
private fun submitSuccessStatTextStyle() = TextStyle(
    fontFamily = pretendardFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 11.dp.toSp(),
    lineHeight = 1.3.em,
    color = primary
)

@Preview
@Composable
private fun SubmitSuccessDialogPreviewWithRewardPoints() {
    val rewardPoints = listOf(
        RewardPoint.Metro(5),
        RewardPoint.Commercial(5),
        RewardPoint.Contribute(5)
    )
    SubmitSuccessDialog(
        rewardPoints = rewardPoints,
        onDismissRequest = {}
    )
}