package com.ilsangtech.ilsang.core.ui.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.ui.quest.model.TypedQuestUiModel
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray200
import com.ilsangtech.ilsang.designsystem.theme.primary300

@Composable
fun QuestCardWithFavorite(
    modifier: Modifier = Modifier,
    quest: TypedQuestUiModel,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val isSmallSize = remember { screenWidth < 400 }

    val contentHorizontalPadding = if (isSmallSize) 18.dp else 23.dp
    val contentVerticalPadding = if (isSmallSize) 16.dp else 20.dp

    DefaultQuestCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = if (!quest.isAvailable) gray200 else Color.White,
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            DefaultQuestContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = contentHorizontalPadding,
                        vertical = contentVerticalPadding
                    ),
                isSmallSize = isSmallSize,
                title = quest.title,
                writer = quest.writerName,
                rewardPoints = quest.rewards,
                isIsZoneQuest = quest.isIsZoneQuest,
                remainHours = quest.remainHours,
                questImage = {
                    QuestImageWithBadge(
                        isSmallSize = isSmallSize,
                        imageId = quest.imageId,
                        questType = quest.questType,
                        contentDescription = "퀘스트 이미지"
                    )
                }
            )
            Icon(
                modifier = modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = if (isSmallSize) 12.dp else 16.dp,
                        end = if (isSmallSize) 12.dp else 16.dp
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
    val quest = TypedQuestUiModel(
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