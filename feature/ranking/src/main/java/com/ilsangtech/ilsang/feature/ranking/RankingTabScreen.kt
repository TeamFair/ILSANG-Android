package com.ilsangtech.ilsang.feature.ranking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.designsystem.R.font.pretendard_bold
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.gray500
import com.ilsangtech.ilsang.feature.ranking.component.AreaRankItem
import com.ilsangtech.ilsang.feature.ranking.component.BoxWithOverlay
import com.ilsangtech.ilsang.feature.ranking.component.RankingTabBanner
import com.ilsangtech.ilsang.feature.ranking.component.RewardTabRow
import com.ilsangtech.ilsang.feature.ranking.component.SeasonSelector
import com.ilsangtech.ilsang.feature.ranking.component.TimeRemainingCard
import com.ilsangtech.ilsang.feature.ranking.component.UserRankItem
import com.ilsangtech.ilsang.feature.ranking.model.AreaRankUiModel
import com.ilsangtech.ilsang.feature.ranking.model.RankingTabUiState
import com.ilsangtech.ilsang.feature.ranking.model.RewardUiModel
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel
import com.ilsangtech.ilsang.feature.ranking.model.UserRankUiModel
import com.ilsangtech.ilsang.feature.ranking.navigation.RankingDetailRoute
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RankingTabScreen(
    rankingViewModel: RankingTabViewModel = hiltViewModel(),
    navigateToRankingDetail: (RankingDetailRoute) -> Unit
) {
    val seasonList by rankingViewModel.seasonList.collectAsStateWithLifecycle()
    val currentSeason by rankingViewModel.currentSeason.collectAsStateWithLifecycle()
    val selectedSeason by rankingViewModel.selectedSeason.collectAsStateWithLifecycle()

    val rankingUiState by rankingViewModel.rankingTabUiState.collectAsStateWithLifecycle()

    RankingTabScreen(
        seasonList = seasonList,
        currentSeason = currentSeason,
        selectedSeason = selectedSeason,
        rankingTabUiState = rankingUiState,
        onSeasonSelected = rankingViewModel::updateSelectedSeason,
        onAreaClick = { areaRankUiModel, isMetro ->
            navigateToRankingDetail(
                RankingDetailRoute(
                    seasonId = currentSeason!!.seasonId,
                    isMetro = isMetro,
                    areaCode = areaRankUiModel.areaCode,
                    areaName = areaRankUiModel.areaName,
                    rank = areaRankUiModel.rank,
                    point = areaRankUiModel.point,
                    images = areaRankUiModel.images
                )
            )
        },
        onSeasonFinished = rankingViewModel::refreshSeason
    )
}


@Composable
private fun RankingTabScreen(
    seasonList: List<SeasonUiModel>,
    currentSeason: SeasonUiModel.Season?,
    selectedSeason: SeasonUiModel,
    rankingTabUiState: RankingTabUiState,
    onSeasonSelected: (SeasonUiModel) -> Unit,
    onAreaClick: (AreaRankUiModel, Boolean) -> Unit,
    onSeasonFinished: () -> Unit
) {
    var selectedReward by remember { mutableStateOf(RewardUiModel.Metro) }
    var expanded by remember { mutableStateOf(false) }
    val endDate = remember(currentSeason) {
        currentSeason?.let {
            SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                .parse(currentSeason.endDate)
        }
    }
    var popupOffset by remember { mutableStateOf(Offset(0f, 0f)) }

    if (expanded) {
        Popup {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(popupOffset.x.toInt(), popupOffset.y.toInt()) }
                    .background(Color.Black.copy(alpha = 0.3f))
            )
        }
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
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    popupOffset =
                        Offset(
                            x = 0f,
                            y = coordinates.positionInWindow().y
                                    + coordinates.size.height.toFloat()
                        )

                },
                expanded = expanded,
                seasonList = seasonList,
                selectedSeason = selectedSeason,
                onSeasonSelected = onSeasonSelected,
                onExpandedChange = { expanded = it }
            )
            Column(modifier = Modifier.weight(1f)) {
                currentSeason?.let {
                    RankingTabBanner(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 24.dp),
                        season = currentSeason,
                        onQuestButtonClick = {}
                    )
                }
                RewardTabRow(
                    selectedReward = selectedReward,
                    onRewardSelect = { selectedReward = it }
                )
                BoxWithOverlay(
                    modifier = Modifier.fillMaxSize(),
                    overlay = {
                        if (endDate != null) {
                            TimeRemainingCard(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .padding(bottom = 20.dp),
                                seasonNumber = currentSeason!!.seasonNumber,
                                endDate = endDate,
                                onSeasonFinished = onSeasonFinished
                            )
                        }
                    }
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(background),
                        contentPadding = PaddingValues(
                            top = 16.dp, bottom = paddingValues.calculateBottomPadding() + 20.dp,
                            start = 20.dp, end = 20.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (rankingTabUiState is RankingTabUiState.Success) {
                            val (metroRankAreas, commercialRankAreas, contributionRankUsers) = rankingTabUiState
                            when (selectedReward) {
                                RewardUiModel.Metro -> {
                                    items(metroRankAreas) { metroRankArea ->
                                        AreaRankItem(
                                            areaName = metroRankArea.areaName,
                                            rank = metroRankArea.rank,
                                            point = metroRankArea.point,
                                            onClick = { onAreaClick(metroRankArea, true) }
                                        )
                                    }
                                }

                                RewardUiModel.Commerical -> {
                                    items(commercialRankAreas) { commercialRankArea ->
                                        AreaRankItem(
                                            areaName = commercialRankArea.areaName,
                                            rank = commercialRankArea.rank,
                                            point = commercialRankArea.point,
                                            onClick = { onAreaClick(commercialRankArea, false) }
                                        )
                                    }
                                }

                                RewardUiModel.Contribute -> {
                                    items(contributionRankUsers) { contributionRankUser ->
                                        UserRankItem(
                                            nickname = contributionRankUser.nickname,
                                            imageId = contributionRankUser.profileImageId,
                                            rank = contributionRankUser.rank,
                                            point = contributionRankUser.point,
                                            titleName = contributionRankUser.titleName,
                                            titleGrade = contributionRankUser.titleGrade
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RankingScreenHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
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
    val seasonList = listOf(
        SeasonUiModel.Total,
        SeasonUiModel.Season(
            seasonId = 1,
            seasonNumber = 1,
            startDate = "2023.01.01",
            endDate = "2023.03.31"
        ),
        SeasonUiModel.Season(
            seasonId = 2,
            seasonNumber = 2,
            startDate = "2023.04.01",
            endDate = "2023.06.30"
        )
    )
    val currentSeason = SeasonUiModel.Season(
        seasonId = 2,
        seasonNumber = 2,
        startDate = "2023.04.01",
        endDate = "2023.06.30"
    )
    val selectedSeason = SeasonUiModel.Season(
        seasonId = 2,
        seasonNumber = 2,
        startDate = "2023.04.01",
        endDate = "2023.06.30"
    )
    val rankingTabUiState = RankingTabUiState.Success(
        metroRankAreas = listOf(
            AreaRankUiModel(
                areaCode = "",
                areaName = "강남구",
                rank = 1,
                point = 1000,
                images = emptyList()
            ),
            AreaRankUiModel(
                areaCode = "",
                areaName = "서초구",
                rank = 2,
                point = 900,
                images = emptyList()
            ),
            AreaRankUiModel(
                areaCode = "",
                areaName = "송파구",
                rank = 3,
                point = 800,
                images = emptyList()
            )
        ),
        commercialRankAreas = listOf(
            AreaRankUiModel(
                areaCode = "",
                areaName = "역삼동",
                rank = 1,
                point = 1200,
                images = emptyList()
            ),
            AreaRankUiModel(
                areaCode = "",
                areaName = "삼성동",
                rank = 2,
                point = 1100,
                images = emptyList()
            ),
            AreaRankUiModel(
                areaCode = "",
                areaName = "논현동",
                rank = 3,
                point = 1000,
                images = emptyList()
            )
        ),
        contributionRankUsers = listOf(
            UserRankUiModel(
                nickname = "홍길동",
                userId = "",
                profileImageId = null,
                rank = 1,
                point = 1500,
                titleName = "명예시민",
                titleGrade = TitleGrade.Standard,

                ),
            UserRankUiModel(
                nickname = "김철수",
                userId = "",
                profileImageId = null,
                rank = 2,
                point = 1400,
                titleName = "우수시민",
                titleGrade = TitleGrade.Rare
            )
        )
    )
    RankingTabScreen(
        seasonList = seasonList,
        currentSeason = currentSeason,
        selectedSeason = selectedSeason,
        rankingTabUiState = rankingTabUiState,
        onSeasonSelected = {},
        onAreaClick = { _, _ -> },
        onSeasonFinished = {}
    )
}