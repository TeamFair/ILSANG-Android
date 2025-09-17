package com.ilsangtech.ilsang.feature.ranking.component

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilsangtech.ilsang.designsystem.theme.gray100
import com.ilsangtech.ilsang.designsystem.theme.gray300
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.pretendardFontFamily
import com.ilsangtech.ilsang.designsystem.theme.primary
import com.ilsangtech.ilsang.feature.ranking.model.RewardUiModel


@Composable
internal fun SeasonRewardTypeTabRow(
    modifier: Modifier = Modifier,
    selectedRewardType: RewardUiModel,
    onRewardTypeSelected: (RewardUiModel) -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedRewardType.ordinal,
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            if (selectedRewardType.ordinal < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        tabPositions[selectedRewardType.ordinal]
                    ),
                    width = 80.dp,
                    height = 3.dp,
                    color = primary
                )
            }
        },
        divider = { HorizontalDivider(color = gray100) },
        tabs = {
            RewardUiModel.entries.forEach { rewardType ->
                Tab(
                    selected = selectedRewardType == rewardType,
                    selectedContentColor = gray500,
                    unselectedContentColor = gray300,
                    onClick = { onRewardTypeSelected(rewardType) }
                ) {
                    Text(
                        modifier = Modifier.padding(
                            top = 11.dp, bottom = 9.dp
                        ),
                        text = rewardType.toString(),
                        style = TextStyle(
                            fontFamily = pretendardFontFamily,
                            fontSize = 14.sp,
                            lineHeight = 24.sp,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        ),
                        fontWeight = if (selectedRewardType == rewardType) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Normal
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SeasonRewardTypeTabRowPreview() {
    SeasonRewardTypeTabRow(
        selectedRewardType = RewardUiModel.Metro,
        onRewardTypeSelected = {}
    )
}


