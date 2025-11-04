package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
internal fun RemainHoursBadge(
    modifier: Modifier = Modifier,
    remainHours: Int
) {
    val text = if (remainHours >= 24) {
        "${remainHours / 24 + if (remainHours % 24 != 0) 1 else 0}일 후 다시 시작"
    } else {
        "${remainHours}시간 후 다시 시작"
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(gray300),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 2.dp
            ),
            text = text,
            style = caption02.copy(
                fontSize = 12.dp.toSp(),
                lineHeight = 16.dp.toSp()
            )
        )
    }
}

@Preview
@Composable
private fun RemainHoursBadgeHoursPreview() {
    RemainHoursBadge(remainHours = 12)
}

@Preview
@Composable
private fun RemainHoursBadgeDaysPreview() {
    RemainHoursBadge(remainHours = 25)
}
