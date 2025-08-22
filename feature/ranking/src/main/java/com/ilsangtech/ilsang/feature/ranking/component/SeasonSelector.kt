package com.ilsangtech.ilsang.feature.ranking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilsangtech.ilsang.designsystem.R
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.designsystem.theme.heading01
import com.ilsangtech.ilsang.designsystem.theme.subTitle01
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel

@Composable
internal fun SeasonSelector(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    seasonList: List<SeasonUiModel>,
    selectedSeason: SeasonUiModel,
    onExpandedChange: (Boolean) -> Unit,
    onSeasonSelected: (SeasonUiModel) -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = { onExpandedChange(!expanded) }
                )
                .padding(top = 16.dp, bottom = 20.dp, start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$selectedSeason",
                style = heading01,
                color = gray500
            )
            Icon(
                painter = painterResource(
                    if (expanded) R.drawable.icon_under else R.drawable.icon_up
                ),
                contentDescription = null,
                tint = gray500
            )
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            expanded = expanded,
            containerColor = Color.White,
            shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
            shadowElevation = 0.dp,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            seasonList.forEach { season ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                onSeasonSelected(season)
                                onExpandedChange(false)
                            }
                        )
                        .padding(top = 14.dp, bottom = 18.dp, start = 20.dp)
                ) {
                    Text(
                        text = "$season",
                        style = subTitle01.copy(fontWeight = FontWeight.Normal),
                        color = gray500
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonSelectorPreview() {
    val seasonList = listOf(
        SeasonUiModel.Total,
        SeasonUiModel.Season(
            seasonId = 1,
            seasonNumber = 1,
            startDate = "2023-01-01",
            endDate = "2023-03-31"
        ),
        SeasonUiModel.Season(
            seasonId = 1,
            seasonNumber = 2,
            startDate = "2023-04-01",
            endDate = "2023-06-30"
        )
    )
    var expanded by remember { mutableStateOf(false) }
    val selectedSeason by remember { mutableStateOf(seasonList[1]) }
    SeasonSelector(
        expanded = expanded,
        seasonList = seasonList,
        selectedSeason = selectedSeason,
        onExpandedChange = { expanded = it },
        onSeasonSelected = {}
    )
}