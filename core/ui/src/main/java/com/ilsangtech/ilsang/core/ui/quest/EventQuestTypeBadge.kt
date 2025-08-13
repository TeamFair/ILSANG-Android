package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun EventQuestTypeBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(3.2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_event),
                tint = Color.Unspecified,
                contentDescription = "한정"
            )
            Text(
                text = "한정",
                style = badge02TextStyle.copy(
                    fontSize = 10.dp.toSp(),
                    lineHeight = 12.dp.toSp(),
                    letterSpacing = (-0.3).dp.toSp()
                ),
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun EventQuestTypeBadgePreview() {
    EventQuestTypeBadge()
}