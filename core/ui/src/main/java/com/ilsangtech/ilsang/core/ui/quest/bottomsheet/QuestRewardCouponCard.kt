package com.ilsangtech.ilsang.core.ui.quest.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.tapRegularTextStyle

@Composable
internal fun QuestRewardCouponCard(
    modifier: Modifier = Modifier,
    onRewardButtonClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(containerColor = background),
        border = BorderStroke(width = 1.dp, color = gray100)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "일상존 퀘스트로 \n쿠폰 확률을 높여보세요!",
                style = tapRegularTextStyle,
                color = gray500
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
                onClick = onRewardButtonClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_quest_reward_coupon),
                    tint = Color.Unspecified,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "보상 확인",
                    style = badge01TextStyle
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuestRewardCouponCardPreview() {
    QuestRewardCouponCard {}
}