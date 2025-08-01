package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
fun LargeRewardQuestBadge(
    modifier: Modifier = Modifier,
    xpSum: Int
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .background(
                color = primary,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = "${xpSum}XP",
            style = badge02TextStyle,
            color = Color.White
        )
    }
}