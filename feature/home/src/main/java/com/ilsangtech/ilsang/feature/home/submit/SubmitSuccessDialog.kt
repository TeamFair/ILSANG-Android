package com.ilsangtech.ilsang.feature.home.submit

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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.buttonTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.home.R

@Composable
fun SubmitSuccessDialog(
    modifier: Modifier = Modifier,
    quest: Quest,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
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
                    text = "${quest.rewardList.sumOf { it.quantity }}XP가 상승했어요",
                    style = submitSuccessDialogTitleStyle
                )
                Spacer(Modifier.height(12.dp))
                SubmitSuccessStatsContent(quest.rewardList)
                Spacer(Modifier.height(12.dp))
                Button(
                    modifier = Modifier.width(228.dp),
                    onClick = onDismiss,
                    contentPadding = PaddingValues(vertical = 16.dp),
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
fun SubmitSuccessStatsContent(rewardList: List<Reward>) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(background)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            rewardList.chunked(3).forEach { rewardChunk ->
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    rewardChunk.forEach { reward ->
                        SubmitSuccessStatBox(reward)
                    }
                }
            }
        }
    }
}

@Composable
fun SubmitSuccessStatBox(reward: Reward) {
    val painterResource = painterResource(
        when (reward.content) {
            RewardType.STRENGTH.name -> {
                R.drawable.strength
            }

            RewardType.INTELLECT.name -> {
                R.drawable.bulb
            }

            RewardType.CHARM.name -> {
                R.drawable.heart
            }

            RewardType.FUN.name -> {
                R.drawable.`fun`
            }

            else -> {
                R.drawable.social
            }
        }
    )
    Box(
        modifier = Modifier.height(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource,
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                modifier = Modifier.padding(horizontal = 7.dp),
                text = "${reward.quantity}P",
                textAlign = TextAlign.Center,
                style = submitSuccessStatTextStyle
            )
        }
    }
}

private val submitSuccessDialogTitleStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 17.sp,
    lineHeight = 18.sp,
    color = gray500
)

private val submitSuccessStatTextStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 11.sp,
    lineHeight = 1.3.em,
    color = primary
)

@Preview
@Composable
private fun SubmitSuccessDialogPreview() {
    val previewReward = Reward(
        content = "",
        quantity = 200,
        rewardId = "",
        type = "INTELLECT",
        discountRate = 0,
        questId = "",
        target = "",
        title = ""
    )
    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SubmitSuccessDialogIcon()
            Spacer(Modifier.height(8.dp))
            Text(
                text = "200XP가 상승했어요",
                style = submitSuccessDialogTitleStyle
            )
            Spacer(Modifier.height(12.dp))
            SubmitSuccessStatsContent(
                listOf(
                    previewReward.copy(content = RewardType.STRENGTH.name),
                    previewReward.copy(content = RewardType.INTELLECT.name),
                    previewReward.copy(content = RewardType.CHARM.name),
                    previewReward.copy(content = RewardType.FUN.name),
                    previewReward.copy(content = RewardType.SOCIABILITY.name),
                )
            )
            Spacer(Modifier.height(12.dp))
            Button(
                modifier = Modifier.width(228.dp),
                onClick = {},
                contentPadding = PaddingValues(vertical = 16.dp),
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