package com.ilsangtech.ilsang.feature.home.quest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_semibold
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.primary

@Composable
fun QuestTapHeader(
    selectedQuestType: QuestType,
    selectedRewardType: RewardType,
    onSelectQuestType: (QuestType) -> Unit,
    onSelectRewardType: (RewardType) -> Unit
) {
    Column {
        QuestTypeTabRow(
            selectedQuestType = selectedQuestType,
            onSelectedQuestType = onSelectQuestType
        )
        Spacer(Modifier.height(5.dp))
        RewardTypeTabRow(
            selectedRewardType,
            onSelectRewardType
        )
    }
}

@Composable
fun QuestTypeTabRow(
    selectedQuestType: QuestType,
    onSelectedQuestType: (QuestType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuestType.entries.forEach { questType ->
            Box(
                modifier = Modifier
                    .clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { onSelectedQuestType(questType) }
                    )
            ) {
                Text(
                    text = questType.title,
                    style = questTypeTabTitleStyle,
                    color = if (selectedQuestType == questType) gray500 else gray300
                )
            }
        }
    }
}

@Composable
fun RewardTypeTabRow(
    selectedRewardType: RewardType,
    onSelectRewardType: (RewardType) -> Unit
) {
    TabRow(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.Transparent,
        selectedTabIndex = RewardType.entries.indexOf(selectedRewardType),
        indicator = { tabPositions ->
            if (RewardType.entries.indexOf(selectedRewardType) < tabPositions.size)
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        currentTabPosition =
                        tabPositions[RewardType.entries.indexOf(selectedRewardType)]
                    ),
                    color = primary,
                    shape = RectangleShape,
                    width = 40.dp,
                    height = 3.dp
                )
        },
        divider = {
            HorizontalDivider(
                color = gray100,
                thickness = 0.4.dp
            )
        }
    ) {
        RewardType.entries.forEach { rewardType ->
            Tab(
                selected = selectedRewardType == rewardType,
                onClick = { onSelectRewardType(rewardType) },
                selectedContentColor = gray500,
                unselectedContentColor = gray300
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = rewardType.title,
                    style = rewardTypeTabTitleStyle,
                )
            }
        }
    }
}

private val questTypeTabTitleStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_bold)),
    fontSize = 21.sp,
    lineHeight = 1.4.em
)

private val rewardTypeTabTitleStyle = TextStyle(
    fontFamily = FontFamily(Font(pretendard_semibold)),
    fontSize = 14.sp,
    lineHeight = 24.sp
)

@Preview(showBackground = true)
@Composable
fun QuestTypeTabRowPreview() {
    QuestTypeTabRow(
        selectedQuestType = QuestType.REPEAT,
    ) {}
}

@Preview(showBackground = true)
@Composable
fun RewardTypeTabRowPreview() {
    RewardTypeTabRow(selectedRewardType = RewardType.STRENGTH) {}
}
