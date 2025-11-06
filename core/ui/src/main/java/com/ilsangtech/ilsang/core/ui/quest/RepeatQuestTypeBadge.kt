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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.toSp


@Composable
fun RepeatQuestTypeBadge(
    modifier: Modifier = Modifier,
    isSmallSize: Boolean = false,
    repeatType: QuestType.Repeat
) {
    val badgeWidth = if (isSmallSize) 32.dp else 40.dp
    val badgeHeight = if (isSmallSize) 16.dp else 20.dp

    val textStyle = if (isSmallSize) {
        TextStyle(
            fontFamily = pretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 8.dp.toSp(),
            lineHeight = 9.6.dp.toSp(),
            letterSpacing = (-0.24).dp.toSp()
        )
    } else {
        badge02TextStyle.copy(
            fontSize = 10.dp.toSp(),
            lineHeight = 12.dp.toSp(),
            letterSpacing = (-0.3).dp.toSp()
        )
    }


    val badgeBrush = when (repeatType) {
        QuestType.Repeat.Daily -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF7D17FF),
                Color(0xFFA45DFF)
            )
        )

        QuestType.Repeat.Weekly -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF45009E),
                Color(0xFF7000FF)
            )
        )

        QuestType.Repeat.Monthly -> Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF00DA30),
                Color(0xFFD1FF05)
            )
        )
    }

    Box(
        modifier = modifier
            .size(
                width = badgeWidth,
                height = badgeHeight
            )
            .background(
                brush = badgeBrush,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (repeatType) {
                QuestType.Repeat.Daily -> "일간"
                QuestType.Repeat.Weekly -> "주간"
                QuestType.Repeat.Monthly -> "월간"
            },
            style = textStyle,
            color = if (repeatType is QuestType.Repeat.Monthly) {
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
private fun RepeatQuestTypeBadgePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RepeatQuestTypeBadge(repeatType = QuestType.Repeat.Daily)
        RepeatQuestTypeBadge(repeatType = QuestType.Repeat.Weekly)
        RepeatQuestTypeBadge(repeatType = QuestType.Repeat.Monthly)
    }
}