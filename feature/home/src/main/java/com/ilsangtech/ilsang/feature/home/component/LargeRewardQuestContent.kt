package com.ilsangtech.ilsang.feature.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.ui.quest.QuestCardWithArrow
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.bodyTextStyle
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray500

@Composable
internal fun LargeRewardQuestContent(
    modifier: Modifier = Modifier,
    largeRewardQuests: List<LargeRewardQuest>,
    onQuestClick: (questId: Int) -> Unit,
    onMoreButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "큰 보상 퀘스트",
            style = largeRewardQuestsContentTitleStyle
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            largeRewardQuests.forEach { quest ->
                QuestCardWithArrow(
                    quest = quest,
                    onClick = { onQuestClick(quest.questId) }
                )
            }
        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = 1.dp,
                color = gray100
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = gray500
            ),
            shape = RoundedCornerShape(5.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = onMoreButtonClick
        ) {
            Text(
                text = "더 많은 퀘스트 보기",
                style = bodyTextStyle
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(com.ilsangtech.ilsang.designsystem.R.drawable.icon_right),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LargeRewardQuestContentPreview() {
    val largeRewardQuests = listOf(
        LargeRewardQuest(
            questId = 1,
            expireDate = "2023-12-31",
            imageId = "sample_image_1",
            mainImageId = "sample_main_image_1",
            rewards = listOf(
                RewardPoint.Metro(5),
                RewardPoint.Commercial(10),
                RewardPoint.Contribute(15)
            ),
            title = "Sample Quest 1",
            writerName = "Writer 1"
        ),
        LargeRewardQuest(
            questId = 2,
            expireDate = "2024-01-15",
            imageId = "sample_image_2",
            mainImageId = "sample_main_image_2",
            rewards = listOf(
                RewardPoint.Metro(3),
                RewardPoint.Commercial(8),
                RewardPoint.Contribute(12)
            ),
            title = "Sample Quest 2",
            writerName = "Writer 2"
        ),
        LargeRewardQuest(
            questId = 3,
            expireDate = "2024-01-15",
            imageId = "sample_image_3",
            mainImageId = "sample_main_image_3",
            rewards = listOf(
                RewardPoint.Metro(3),
                RewardPoint.Commercial(8),
                RewardPoint.Contribute(12)
            ),
            title = "Sample Quest 2",
            writerName = "Writer 2"
        )
    )
    LargeRewardQuestContent(
        largeRewardQuests = largeRewardQuests,
        onQuestClick = {},
        onMoreButtonClick = {}
    )
}

private val largeRewardQuestsContentTitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.sp,
    fontFamily = FontFamily(Font(pretendard_semibold))
)
