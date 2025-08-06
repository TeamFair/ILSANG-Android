package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.ui.BuildConfig

@Composable
fun DefaultQuestImage(
    modifier: Modifier = Modifier,
    imageId: String?,
    contentDescription: String,
) {
    AsyncImage(
        modifier = modifier
            .zIndex(1f)
            .size(60.dp)
            .clip(CircleShape)
            .background(defaultQuestImageBackgroundColor),
        model = BuildConfig.IMAGE_URL + imageId,
        contentDescription = contentDescription
    )
}

private val defaultQuestImageBackgroundColor = Color(0xFFF1F5FF)

@Preview
@Composable
fun DefaultQuestImagePreview() {
    DefaultQuestImage(
        imageId = "sampleImageId",
        contentDescription = "Sample quest image"
    )
}

