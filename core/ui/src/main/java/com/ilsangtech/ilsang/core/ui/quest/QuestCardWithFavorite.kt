package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.Reward
import com.ilsangtech.ilsang.core.model.RewardPoint
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
        questType = NewQuestType.Normal
    )
    QuestCardWithFavorite(
        quest = quest,
        onFavoriteClick = {},
        onClick = {}
    )
}


@Composable
fun QuestCardWithFavorite(
    modifier: Modifier = Modifier,
    quest: Quest,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    DefaultQuestCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultQuestContent(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        horizontal = 23.dp,
                        vertical = 20.dp
                    ),
                quest = quest,
                questImage = {
                    QuestImageWithBadge(
                        imageId = quest.imageId,
                        contentDescription = "퀘스트 이미지"
                    )
                }
            )
            Icon(
                modifier = modifier
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
private fun QuestCardWithFavoritePreview() {
    val quest = Quest(
        createDate = "2023-10-27",
        creatorRole = "Admin",
        expireDate = "2023-11-27",
        favoriteYn = true,
        imageId = null,
        mainImageId = null,
        marketId = null,
        missionId = "mission123",
        missionTitle = "Complete the survey",
        missionType = "Survey",
        popularYn = false,
        questId = "quest123",
        rewardList = listOf(
            Reward(
                content = "Gift card",
                discountRate = null,
                quantity = 1,
                questId = "quest123",
                rewardId = "reward123",
                target = null,
                title = "Amazon Gift Card",
                type = "Gift"
            )
        ),
        score = 100,
        status = "Active",
        target = "All users",
        type = "General",
        writer = "John Doe"
    )
    QuestCardWithFavorite(
        quest = quest,
        onFavoriteClick = {},
        onClick = {}
    )
}
