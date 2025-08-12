package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.designsystem.R


@Composable
internal fun RewardPointIcon(
    modifier: Modifier = Modifier,
    rewardPoint: RewardPoint
) {
    Icon(
        modifier = modifier,
        painter = painterResource(
            when (rewardPoint) {
                is RewardPoint.Metro -> R.drawable.icon_metro_reward
                is RewardPoint.Commerical -> R.drawable.icon_commercial_reward
                is RewardPoint.Contribute -> R.drawable.icon_contribute_reward
            }
        ),
        tint = Color.Unspecified,
        contentDescription = "보상 포인트 아이콘"
    )
}