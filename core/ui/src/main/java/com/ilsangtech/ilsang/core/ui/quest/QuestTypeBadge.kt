package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle

@Composable
fun QuestTypeBadge(
    modifier: Modifier = Modifier,
    repeatType: String
) {
    val badgeBrush = when (repeatType) {
        "일간" -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF7D17FF),
                Color(0xFFA45DFF)
            )
        )

        "주간" -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF45009E),
                Color(0xFF7000FF)
            )
        )

        else -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF00DA30),
                Color(0xFFD1FF05)
            )
        )
    }

    Box(
        modifier = modifier
            .size(
                width = 40.dp,
                height = 20.dp
            )
            .background(
                brush = badgeBrush,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = repeatType,
            style = badge02TextStyle,
            color = if (repeatType == "월간") {
                monthlyBadgeTextColor
            } else {
                Color.White
            }
        )
    }
}

private val monthlyBadgeTextColor = Color(0xFF005E15)

@Preview(showBackground = true)
@Composable
private fun QuestTypeBadgePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuestTypeBadge(repeatType = "주간")
        QuestTypeBadge(repeatType = "일간")
        QuestTypeBadge(repeatType = "월간")
    }
}