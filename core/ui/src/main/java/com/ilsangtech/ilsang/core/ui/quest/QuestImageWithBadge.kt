package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ilsangtech.ilsang.core.model.QuestType

@Composable
fun QuestImageWithBadge(
    modifier: Modifier = Modifier,
    imageId: String?,
    questType: QuestType,
    contentDescription: String
) {
    Box(modifier = modifier) {
        if (questType is QuestType.Repeat) {
            RepeatQuestTypeBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(2f)
                    .offset(x = 10.dp, y = (-10).dp),
                repeatType = questType
            )
        } else if (questType is QuestType.Event) {
            EventQuestTypeBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(2f)
                    .offset(x = 10.dp, y = (-10).dp)
            )
        }
        DefaultQuestImage(
            imageId = imageId,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun QuestImageWithBadgeRepeatPreview() {
    QuestImageWithBadge(
        imageId = null,
        questType = QuestType.Repeat.Daily,
        contentDescription = "Quest Image"
    )
}

@Preview
@Composable
private fun QuestImageWithBadgeEventPreview() {
    QuestImageWithBadge(
        imageId = null,
        questType = QuestType.Event,
        contentDescription = "Quest Image"
    )
}

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
                    .zIndex(2f)
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