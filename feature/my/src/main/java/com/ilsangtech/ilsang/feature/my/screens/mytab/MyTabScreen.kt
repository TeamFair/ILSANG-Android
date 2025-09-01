package com.ilsangtech.ilsang.feature.my.screens.mytab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.model.title.UserTitle
import com.ilsangtech.ilsang.core.ui.season.model.SeasonUiModel
import com.ilsangtech.ilsang.core.ui.user.model.TopCommercialAreaUiModel
import com.ilsangtech.ilsang.core.ui.user.model.TotalOwnerContributionUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserCommercialPointUiModel
import com.ilsangtech.ilsang.core.ui.user.model.UserObtainedPointUiModel
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.my.screens.mytab.component.MyCommercialPointContent
import com.ilsangtech.ilsang.feature.my.screens.mytab.component.MyObtainedPointContent
import com.ilsangtech.ilsang.feature.my.screens.mytab.component.MyPointSummaryContent
import com.ilsangtech.ilsang.feature.my.screens.mytab.component.MyProfileInfoCard
import com.ilsangtech.ilsang.feature.my.screens.mytab.component.MyTabHeader
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyPointSummaryUiModel
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyProfileInfoUiModel
import com.ilsangtech.ilsang.feature.my.screens.mytab.model.MyTabScreenUiState

@Composable
internal fun MyTabScreen(
    viewModel: MyTabViewModel = hiltViewModel(),
    onProfileEditButtonClick: (nickname: String, profileImageId: String?) -> Unit,
    onMissionHistoryButtonClick: () -> Unit,
    onSettingButtonClick: () -> Unit,
    onQuestNavButtonClick: () -> Unit,
    onTitleClick: (Int?) -> Unit
) {
    val uiState by viewModel.myTabScreenUiState.collectAsStateWithLifecycle()
    val selectedSeason by viewModel.selectedSeason.collectAsStateWithLifecycle()

    MyTabScreen(
        uiState = uiState,
        selectedSeason = selectedSeason,
        onSeasonChanged = viewModel::updateSeason,
        onSettingButtonClick = onSettingButtonClick,
        onTitleClick = onTitleClick,
        onMissionHistoryButtonClick = onMissionHistoryButtonClick,
        onFavoriteQuestButtonClick = {},
        onCouponButtonClick = {},
        onQuestNavButtonClick = onQuestNavButtonClick,
        onProfileEditButtonClick = onProfileEditButtonClick
    )
}

@Composable
private fun MyTabScreen(
    uiState: MyTabScreenUiState,
    selectedSeason: SeasonUiModel,
    onSeasonChanged: (SeasonUiModel) -> Unit,
    onSettingButtonClick: () -> Unit,
    onProfileEditButtonClick: (nickname: String, profileImageId: String?) -> Unit,
    onTitleClick: (Int?) -> Unit,
    onMissionHistoryButtonClick: () -> Unit,
    onFavoriteQuestButtonClick: () -> Unit,
    onCouponButtonClick: () -> Unit,
    onQuestNavButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MyTabHeader(
                modifier = Modifier.statusBarsPadding(),
                onSettingButtonClick = onSettingButtonClick
            )
            if (uiState is MyTabScreenUiState.Success) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(48.dp),
                    contentPadding = PaddingValues(
                        top = 6.dp, bottom = 72.dp,
                        start = 20.dp, end = 20.dp
                    )
                ) {
                    item {
                        MyProfileInfoCard(
                            myProfileInfo = uiState.myProfileInfo,
                            onProfileEditButtonClick = {
                                onProfileEditButtonClick(
                                    uiState.myProfileInfo.nickname,
                                    uiState.myProfileInfo.profileImageId
                                )
                            },
                            onTitleClick = { onTitleClick(uiState.myProfileInfo.userTitle?.titleHistoryId) },
                            onMissionHistoryButtonClick = onMissionHistoryButtonClick,
                            onFavoriteQuestButtonClick = onFavoriteQuestButtonClick,
                            onCouponButtonClick = onCouponButtonClick
                        )
                    }
                    item {
                        MyCommercialPointContent(
                            myCommercialPoint = uiState.myCommercialPoint,
                            onQuestNavButtonClick = onQuestNavButtonClick
                        )
                    }
                    item {
                        MyObtainedPointContent(
                            myPoint = uiState.myObtainedPoint,
                            selectedSeason = selectedSeason,
                            onSeasonChanged = onSeasonChanged
                        )
                    }
                    item {
                        MyPointSummaryContent(
                            myPointSummary = uiState.myPointSummary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyTabScreenPreview() {
    val uiState = MyTabScreenUiState.Success(
        myProfileInfo = MyProfileInfoUiModel(
            nickname = "김일상123닉네임 길이가 길어",
            profileImageId = "",
            levelProgress = 0.5f,
            level = 1,
            userTitle = UserTitle(
                titleHistoryId = 1,
                title = Title(
                    name = "세상을 움직이는 자",
                    grade = TitleGrade.Standard,
                    type = TitleType.Metro
                )
            )
        ),
        myCommercialPoint = UserCommercialPointUiModel(
            nickname = "User",
            topCommercialArea = TopCommercialAreaUiModel(
                commercialAreaName = "강남역",
                contributionPercent = 70,
                point = 1000
            ),
            totalOwnerContributions = listOf(
                TotalOwnerContributionUiModel(
                    commercialAreaName = "역삼역",
                    point = 500
                ),
                TotalOwnerContributionUiModel(
                    commercialAreaName = "선릉역",
                    point = 300
                ),
                TotalOwnerContributionUiModel(
                    commercialAreaName = "삼성역",
                    point = 300
                )
            )
        ),
        myObtainedPoint = UserObtainedPointUiModel(
            completedQuestCount = 10,
            metroAreaPoint = RewardPoint.Metro(100),
            commercialAreaPoint = RewardPoint.Commercial(200),
            contributionPoint = RewardPoint.Contribute(300),
            seasonList = listOf(
                SeasonUiModel.Total,
                SeasonUiModel.Specific(id = 1, seasonNumber = 1),
                SeasonUiModel.Specific(id = 2, seasonNumber = 2)
            )
        ),
        myPointSummary = MyPointSummaryUiModel(
            nickName = "김일상123닉네임 길이가 길어",
            topCommercialAreaName = "강남역",
            topMetroAreaName = "강남구",
            topContributionPoint = 1000,
            seasonNumber = 1,
            seasonStartDate = "2023.01.01",
            seasonEndDate = "2023.03.31"
        )
    )
    val selectedSeason = SeasonUiModel.Total
    MyTabScreen(
        uiState = uiState,
        selectedSeason = selectedSeason,
        onSeasonChanged = {},
        onSettingButtonClick = {},
        onProfileEditButtonClick = { _, _ -> },
        onTitleClick = {},
        onMissionHistoryButtonClick = {},
        onFavoriteQuestButtonClick = {},
        onCouponButtonClick = {},
        onQuestNavButtonClick = {})
}