package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.designsystem.theme.toSp
import com.ilsangtech.ilsang.feature.ranking.model.RewardUiModel

@Composable
internal fun RewardTabRow(
    selectedReward: RewardUiModel,
    onRewardSelect: (RewardUiModel) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedReward.ordinal,
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            if (selectedReward.ordinal < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        tabPositions[selectedReward.ordinal]
                    ),
                    width = 80.dp,
                    height = 3.dp,
                    color = primary
                )
            }
        },
        divider = { HorizontalDivider(color = gray100) }
    ) {
        RewardUiModel.entries.forEach { reward ->
            Tab(
                modifier = Modifier.height(44.dp),
                selected = reward == selectedReward,
                selectedContentColor = gray500,
                unselectedContentColor = gray300,
                onClick = { onRewardSelect(reward) }
            ) {
                Text(
                    text = "$reward",
                    style = TextStyle(
                        fontFamily = pretendardFontFamily,
                        fontWeight =
                            if (reward == selectedReward) {
                                FontWeight.SemiBold
                            } else {
                                FontWeight.Normal
                            },
                        fontSize = 14.dp.toSp(),
                        lineHeight = 24.dp.toSp()
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun RewardTabRowPreview() {
    RewardTabRow(
        selectedReward = RewardUiModel.Metro,
        onRewardSelect = {}
    )
}