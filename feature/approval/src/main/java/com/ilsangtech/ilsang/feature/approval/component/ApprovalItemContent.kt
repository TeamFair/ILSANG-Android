package com.ilsangtech.ilsang.feature.approval.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.badge01TextStyle
import com.ilsangtech.ilsang.designsystem.theme.caption02
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.approval.BuildConfig

@Composable
internal fun ApprovalItemContent(
    modifier: Modifier = Modifier,
    challengeImage: String,
    createdAt: String,
    areaName: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = painterResource(R.drawable.icon_metro),
                    tint = Color.Unspecified,
                    contentDescription = "지역"
                )
                Text(
                    text = areaName,
                    style = badge01TextStyle,
                    color = gray500
                )
            }
            Text(
                text = createdAt,
                style = caption02,
                color = gray500
            )
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(8 / 7f)
                .clip(RoundedCornerShape(12.dp)),
            model = BuildConfig.IMAGE_URL + challengeImage,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ApprovalItemContentPreview() {
    ApprovalItemContent(
        modifier = Modifier.padding(20.dp),
        challengeImage = "https://picsum.photos/200/300",
        createdAt = "2025.04.12 12:00",
        areaName = "서울"
    )
}