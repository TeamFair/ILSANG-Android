package com.ilsangtech.ilsang.feature.ranking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.designsystem.theme.heading02
import com.ilsangtech.ilsang.feature.ranking.component.MyRankCard
import com.ilsangtech.ilsang.feature.ranking.component.RankingDetailAreaInfoCard
import com.ilsangtech.ilsang.feature.ranking.component.RankingDetailHeader
import com.ilsangtech.ilsang.feature.ranking.component.TimeRemainingCard
import com.ilsangtech.ilsang.feature.ranking.component.UserRankItem
import com.ilsangtech.ilsang.feature.ranking.model.AreaRankUiModel
import com.ilsangtech.ilsang.feature.ranking.model.RankingDetailUiState
import com.ilsangtech.ilsang.feature.ranking.model.SeasonUiModel
import com.ilsangtech.ilsang.feature.ranking.model.UserRankUiModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RankingDetailScreen(
    rankingDetailViewModel: RankingDetailViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val rankingDetailUiState by
    rankingDetailViewModel.rankingDetailUiState.collectAsStateWithLifecycle()

    if (rankingDetailUiState is RankingDetailUiState.Success) {
        val rankingDetailUiState = rankingDetailUiState as RankingDetailUiState.Success

        RankingDetailScreen(
            currentSeason = rankingDetailUiState.currentSeason,
            areaRankUiModel = rankingDetailUiState.areaRankUiModel,
            myRankUiModel = rankingDetailUiState.myRankUiModel,
            userRankList = rankingDetailUiState.userRankList,
            onBackButtonClick = onBackButtonClick,
            onSeasonFinished = rankingDetailViewModel::refreshSeason
        )
    }
}

@Composable
private fun RankingDetailScreen(
    currentSeason: SeasonUiModel.Season,
    areaRankUiModel: AreaRankUiModel,
    myRankUiModel: UserRankUiModel,
    userRankList: List<UserRankUiModel>,
    onBackButtonClick: () -> Unit,
    onSeasonFinished: () -> Unit
) {
    val endDate = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .parse(currentSeason.endDate)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            RankingDetailHeader(onBackButtonClick = onBackButtonClick)
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 20.dp, end = 20.dp, bottom = 72.dp
                    )
                ) {
                    item {
                        RankingDetailAreaInfoCard(areaRankUiModel = areaRankUiModel)
                    }
                    item {
                        Text(
                            modifier = Modifier.padding(top = 48.dp, bottom = 16.dp),
                            text = "유저",
                            style = heading02,
                            color = Color.Black
                        )
                    }
                    item {
                        MyRankCard(
                            imageId = myRankUiModel.profileImageId,
                            nickname = myRankUiModel.nickname,
                            titleName = myRankUiModel.titleName,
                            titleGrade = myRankUiModel.titleGrade,
                            point = myRankUiModel.point,
                            rank = myRankUiModel.rank,
                            requiredPoint = userRankList.find {
                                it.rank == myRankUiModel.rank - 1
                            }?.point?.minus(myRankUiModel.point) ?: 0
                        )
                    }
                    item { Spacer(Modifier.height(12.dp)) }
                    itemsIndexed(userRankList) { index, item ->
                        UserRankItem(
                            imageId = item.profileImageId,
                            nickname = item.nickname,
                            titleName = item.titleName,
                            titleGrade = item.titleGrade,
                            point = item.point,
                            rank = item.rank,
                        )
                        if (index != userRankList.lastIndex) {
                            Spacer(Modifier.height(12.dp))
                        }
                    }
                }
                endDate?.let {
                    TimeRemainingCard(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 20.dp),
                        seasonNumber = currentSeason.seasonNumber,
                        endDate = endDate,
                        onSeasonFinished = onSeasonFinished
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RankingDetailScreenPreview() {
    val areaRankUiModel = AreaRankUiModel(
        areaName = "강남구",
        rank = 1,
        point = 1000,
        images = listOf("image1", "image2", "image3")
    )
    val myRankUiModel = UserRankUiModel(
        userId = "1",
        profileImageId = "profile1",
        nickname = "일상유저123닉네임 길어",
        point = 81223840,
        rank = 10,
        titleName = "모두의 시선을 받는 자",
        titleGrade = TitleGrade.Standard
    )
    val userRankList = listOf(
        UserRankUiModel(
            userId = "2",
            profileImageId = "profile2",
            nickname = "일상유저123닉네임 길어",
            point = 81223840,
            rank = 11,
            titleName = "모두의 시선을 받는 자",
            titleGrade = TitleGrade.Standard
        ),
        UserRankUiModel(
            userId = "3",
            profileImageId = "profile3",
            nickname = "일상유저123닉네임 길어",
            point = 81223840,
            rank = 12,
            titleName = "모두의 시선을 받는 자",
            titleGrade = TitleGrade.Standard
        )
    )
    RankingDetailScreen(
        currentSeason = SeasonUiModel.Season(
            seasonNumber = 1,
            startDate = "2023-01-01",
            endDate = "2025-10-31"
        ),
        areaRankUiModel = areaRankUiModel,
        myRankUiModel = myRankUiModel,
        userRankList = userRankList,
        onBackButtonClick = {},
        onSeasonFinished = {}
    )
}