package com.ilsangtech.ilsang.feature.ranking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.ranking.component.SeasonRewardScreenHeader
import com.ilsangtech.ilsang.feature.ranking.component.SeasonRewardTitleCard
import com.ilsangtech.ilsang.feature.ranking.component.SeasonRewardTypeTabRow
import com.ilsangtech.ilsang.feature.ranking.model.RewardUiModel
import com.ilsangtech.ilsang.feature.ranking.model.SeasonRewardScreenUiState
import com.ilsangtech.ilsang.feature.ranking.model.SeasonRewardTitleUiModel
import com.ilsangtech.ilsang.feature.ranking.model.SeasonRewardTitleUiState

@Composable
internal fun SeasonRewardScreen(
    viewModel: SeasonRewardViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SeasonRewardScreen(
        uiState = uiState,
        onRewardTypeSelected = viewModel::updateTitleType,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun SeasonRewardScreen(
    uiState: SeasonRewardScreenUiState,
    onRewardTypeSelected: (RewardUiModel) -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            SeasonRewardScreenHeader(onBackButtonClick = onBackButtonClick)
            SeasonRewardTypeTabRow(
                selectedRewardType = uiState.selectedType,
                onRewardTypeSelected = onRewardTypeSelected
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(
                    top = 12.dp, bottom = 72.dp,
                    start = 20.dp, end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.selectedRewardTitleState is SeasonRewardTitleUiState.Success) {
                    items(
                        items = uiState.selectedRewardTitleState.seasonRewardTitleList
                    ) { item ->
                        SeasonRewardTitleCard(seasonRewardTitle = item)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SeasonRewardScreenPreview() {
    val uiState = SeasonRewardScreenUiState(
        selectedType = RewardUiModel.Metro,
        selectedRewardTitleState = SeasonRewardTitleUiState.Success(
            seasonRewardTitleList = listOf(
                SeasonRewardTitleUiModel.TopRewardTitle(
                    id = "1",
                    rank = 1,
                    title = Title(
                        name = "첫 번째 칭호",
                        type = TitleType.Metro,
                        grade = TitleGrade.Legend
                    )
                ),
                SeasonRewardTitleUiModel.TopRewardTitle(
                    id = "2",
                    rank = 2,
                    title = Title(
                        name = "두 번째 칭호",
                        type = TitleType.Metro,
                        grade = TitleGrade.Legend
                    )
                ),
                SeasonRewardTitleUiModel.TopRewardTitle(
                    id = "3",
                    rank = 3,
                    title = Title(
                        name = "세 번째 칭호",
                        type = TitleType.Metro,
                        grade = TitleGrade.Legend
                    )
                ),
                SeasonRewardTitleUiModel.OtherRewardTitle(
                    id = "4",
                    rankRange = 4..10,
                    title = Title(
                        name = "마지막 칭호",
                        type = TitleType.Metro,
                        grade = TitleGrade.Rare
                    )
                )
            )
        )
    )
    SeasonRewardScreen(uiState = uiState, onRewardTypeSelected = {}, onBackButtonClick = {})
}
