package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quest.TypedQuest
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.primary300

@Composable
fun QuestCardWithFavorite(
    modifier: Modifier = Modifier,
    quest: TypedQuest,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    DefaultQuestCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            DefaultQuestContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 23.dp,
                        vertical = 20.dp
                    ),
                title = quest.title,
                writer = quest.writerName,
                rewardPoints = quest.rewards,
                questImage = {
                    QuestImageWithBadge(
                        imageId = quest.imageId,
                        badge = {
                            if (quest.questType is QuestType.Repeat) {
                                RepeatQuestTypeBadge(
                                    repeatType = quest.questType as QuestType.Repeat,
                                )
                            } else if (quest.questType is QuestType.Event) {
                                EventQuestTypeBadge()
                            }
                        },
                        contentDescription = "퀘스트 이미지"
                    )
                }
            )
            Icon(
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 16.dp,
                        end = 16.dp
                    )
                    .clickable(
                        onClick = onFavoriteClick,
                        indication = null,
                        interactionSource = null
                    ),
                painter = painterResource(R.drawable.icon_favorite),
                tint = if (quest.favoriteYn) primary300 else gray100,
                contentDescription = "즐겨찾기"
            )
        }
    }
}

@Preview
@Composable
private fun QuestCardWithFavoritePreviewNew() {
    val quest = TypedQuest(
        expireDate = "2023-12-31",
        favoriteYn = true,
        imageId = "sample_image_id",
        mainImageId = "sample_main_image_id",
        questId = 1,
        rewards = listOf(
            RewardPoint.Metro(10),
            RewardPoint.Commercial(5),
            RewardPoint.Contribute(20)
        ),
        title = "Sample Quest Title",
        writerName = "Sample Writer",
        questType = QuestType.Normal
    )
    QuestCardWithFavorite(
        quest = quest,
        onFavoriteClick = {},
        onClick = {}
    )
}