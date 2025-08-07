package com.ilsangtech.ilsang.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    color: Color,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    brush: Brush,
    content: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .height(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(brush),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Preview
@Composable
fun BadgeWithColorPreview() {
    Badge(
        color = Color.Red
    ) {
        Text("Badge")
    }
}

@Preview
@Composable
private fun BadgeWithBrushPreview() {
    Badge(
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Red, Color.Blue)
        )
    ) {
        Text("Badge")
    }
}

