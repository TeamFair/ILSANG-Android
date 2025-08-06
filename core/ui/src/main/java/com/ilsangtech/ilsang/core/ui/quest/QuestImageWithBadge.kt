package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun QuestImageWithBadge(
    modifier: Modifier = Modifier,
    imageId: String?,
    badge: @Composable (() -> Unit)? = null,
    contentDescription: String
) {
    Box(modifier = modifier) {
        badge?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .offset(x = 10.dp, y = (-10).dp)
            ) {
                badge.invoke()
            }
        }
        DefaultQuestImage(
            imageId = imageId,
            contentDescription = contentDescription
        )
    }
}