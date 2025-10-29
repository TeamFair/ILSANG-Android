package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.designsystem.theme.badge02TextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.toSp

@Composable
fun MissionTypeBadge(
    modifier: Modifier = Modifier,
    missionType: MissionType
) {
    Box(
        modifier = modifier
            .size(width = 50.dp, height = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(gray500),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (missionType) {
                MissionType.Photo -> "사진인증"
                MissionType.Ox -> "OX"
                MissionType.Words -> "서술형"
            },
            style = badge02TextStyle.copy(
                fontSize = 10.dp.toSp(),
                lineHeight = 12.dp.toSp()
            ),
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun MissionTypeBadgePhotoPreview() {
    MissionTypeBadge(missionType = MissionType.Photo)
}

@Preview
@Composable
private fun MissionTypeBadgeOXPreview() {
    MissionTypeBadge(missionType = MissionType.Ox)
}

@Preview
@Composable
private fun MissionTypeBadgeWordsPreview() {
    MissionTypeBadge(missionType = MissionType.Words)
}