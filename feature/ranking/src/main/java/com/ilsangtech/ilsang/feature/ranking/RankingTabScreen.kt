package com.ilsangtech.ilsang.feature.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.ranking.component.RankingTabBanner
import com.ilsangtech.ilsang.feature.ranking.component.RewardTabRow
import com.ilsangtech.ilsang.feature.ranking.component.SeasonSelector
import com.ilsangtech.ilsang.feature.ranking.component.StatRankingTabContent
import com.ilsangtech.ilsang.feature.ranking.component.StatRankingTabRow
import com.ilsangtech.ilsang.feature.ranking.component.TimeRemainingCard
import com.ilsangtech.ilsang.feature.ranking.model.RewardUiModel
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RankingTabScreen(
    rankingViewModel: RankingTabViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit
) {
    val rankingUiState by rankingViewModel.rankingUiState.collectAsStateWithLifecycle()
    RankingTabScreen(
        rankingUiState = rankingUiState,
        onRankingItemClick = navigateToProfile
    )
}


@Composable
private fun RankingTabScreen(
    seasonList: List<SeasonUiModel>,
    currentSeason: SeasonUiModel.Season,
    selectedSeason: SeasonUiModel,
    onSeasonSelected: (SeasonUiModel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val endDate = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .parse(currentSeason.endDate)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = Color.White
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            RankingScreenHeader()
            SeasonSelector(
                expanded = expanded,
                seasonList = seasonList,
                selectedSeason = selectedSeason,
                onSeasonSelected = onSeasonSelected,
                onExpandedChange = { expanded = it }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .drawWithContent {
                        drawContent()
                        if (expanded) drawRect(color = Color.Black.copy(alpha = 0.3f))
                    }
            ) {
                RankingTabBanner(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 24.dp),
                    season = currentSeason,
                    onQuestButtonClick = {}
                )
                RewardTabRow(
                    selectedReward = RewardUiModel.Metro,
                    onRewardSelect = {}
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(background),
                        contentPadding = PaddingValues(
                            top = 16.dp, bottom = 72.dp,
                            start = 20.dp, end = 20.dp
                        )
                    ) {}
                    endDate?.let {
                        TimeRemainingCard(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 20.dp)
                                .padding(bottom = 20.dp),
                            seasonNumber = currentSeason.seasonNumber,
                            endDate = endDate,
                            onSeasonFinished = {}
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RankingTabScreen(
    rankingUiState: Map<RewardType, List<UserXpTypeRank>>,
    onRankingItemClick: (String) -> Unit
) {
    var selectedRewardType by remember { mutableStateOf(RewardType.entries.first()) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = background
    ) {
        Column {
            RankingScreenHeader(modifier = Modifier.statusBarsPadding())
            StatRankingTabRow(
                selectedRewardType = selectedRewardType,
                onRewardTypeSelect = { selectedRewardType = it }
            )
            rankingUiState[selectedRewardType]?.let { userXpTypeRanks ->
                StatRankingTabContent(
                    selectedRewardType = selectedRewardType,
                    userXpTypeRanks = userXpTypeRanks,
                    onItemClick = onRankingItemClick
                )
            }
        }
    }
}

@Composable
private fun RankingScreenHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 10.dp,
                horizontal = 20.dp
            )
    ) {
        Text(
            text = "랭킹",
            style = TextStyle(
                fontFamily = FontFamily(Font(pretendard_bold)),
                fontSize = 21.sp,
                lineHeight = 1.4.em,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                ),
                color = gray500
            )
        )
    }
}

@Preview
@Composable
private fun RankingTabScreenPreview() {
    RankingTabScreen(
        rankingUiState = emptyMap(),
        onRankingItemClick = {}
    )
}

@Preview
@Composable
private fun RankingTabScreenPreviewWithSeason() {
    val seasonList = listOf(
        SeasonUiModel.Total,
        SeasonUiModel.Season(1, "2023-01-01", "2025-10-31"),
        SeasonUiModel.Season(2, "2023-04-01", "2025-10-31")
    )
    val selectedSeason = seasonList[1]

    RankingTabScreen(
        seasonList = seasonList,
        currentSeason = selectedSeason as SeasonUiModel.Season,
        selectedSeason = selectedSeason,
        onSeasonSelected = {}
    )
}