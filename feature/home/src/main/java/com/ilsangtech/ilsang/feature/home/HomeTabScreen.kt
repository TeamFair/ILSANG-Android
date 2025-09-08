package com.ilsangtech.ilsang.feature.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.core.model.quest.LargeRewardQuest
import com.ilsangtech.ilsang.core.model.quest.PopularQuest
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.RecommendedQuest
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.model.title.TitleGrade
import com.ilsangtech.ilsang.core.model.title.TitleType
import com.ilsangtech.ilsang.core.ui.quest.bottomsheet.QuestBottomSheet
import com.ilsangtech.ilsang.core.ui.zone.IsZoneSelectDisabledDialog
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.home.component.BannerContent
import com.ilsangtech.ilsang.feature.home.component.HomeTabHeader
import com.ilsangtech.ilsang.feature.home.component.LargeRewardQuestContent
import com.ilsangtech.ilsang.feature.home.component.PopularQuestsContent
import com.ilsangtech.ilsang.feature.home.component.RecommendedQuestsContent
import com.ilsangtech.ilsang.feature.home.component.UserRankContent
import com.ilsangtech.ilsang.feature.home.model.HomeTabSuccessData
import com.ilsangtech.ilsang.feature.home.model.HomeTabUiState
import com.ilsangtech.ilsang.feature.home.model.MyInfoUiModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTabScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToQuestTab: () -> Unit,
    navigateToMyTab: () -> Unit,
    navigateToSubmit: (Int, Int, String) -> Unit,
    navigateToRankingTab: () -> Unit,
    navigateToProfile: (String) -> Unit,
    onMissionImageClick: (Int) -> Unit,
    onBannerClick: (Banner) -> Unit,
    onMyZoneClick: () -> Unit,
    onIsZoneClick: () -> Unit
) {
    val homeTabUiState by homeViewModel.homeTabUiState.collectAsStateWithLifecycle()
    val selectedQuest by homeViewModel.selectedQuest.collectAsStateWithLifecycle()

    HomeTabScreen(
        homeTabUiState = homeTabUiState,
        selectedQuest = selectedQuest,
        navigateToQuestTab = navigateToQuestTab,
        navigateToMyTab = navigateToMyTab,
        navigateToSubmit = navigateToSubmit,
        navigateToRankingTab = navigateToRankingTab,
        navigateToProfile = navigateToProfile,
        onBannerClick = onBannerClick,
        onMyZoneClick = onMyZoneClick,
        onIsZoneClick = onIsZoneClick,
        onMissionImageClick = onMissionImageClick,
        onSelectQuest = homeViewModel::selectQuest,
        onUnselectQuest = homeViewModel::unselectQuest,
        onFavoriteClick = homeViewModel::updateQuestFavoriteStatus,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTabScreen(
    homeTabUiState: HomeTabUiState,
    selectedQuest: QuestDetail?,
    navigateToQuestTab: () -> Unit,
    navigateToMyTab: () -> Unit,
    navigateToSubmit: (Int, Int, String) -> Unit,
    navigateToRankingTab: () -> Unit,
    navigateToProfile: (String) -> Unit,
    onBannerClick: (Banner) -> Unit,
    onMyZoneClick: () -> Unit,
    onIsZoneClick: () -> Unit,
    onMissionImageClick: (Int) -> Unit,
    onSelectQuest: (Int) -> Unit,
    onUnselectQuest: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showZoneSelectDisabledDialog by remember { mutableStateOf(false) }

    if (showZoneSelectDisabledDialog) {
        IsZoneSelectDisabledDialog { showZoneSelectDisabledDialog = false }
    }

    if (selectedQuest != null) {
        QuestBottomSheet(
            quest = selectedQuest,
            bottomSheetState = bottomSheetState,
            onDismiss = onUnselectQuest,
            onFavoriteClick = onFavoriteClick,
            onMissionImageClick = {
                selectedQuest.missions.firstOrNull()?.let { mission ->
                    if (mission.exampleImageIds.isNotEmpty()) {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                            onUnselectQuest()
                            onMissionImageClick(mission.id)
                        }
                    }
                }
            },
            onApproveButtonClick = {
                coroutineScope.launch {
                    val mission = selectedQuest.missions.firstOrNull()
                    bottomSheetState.hide()
                    onUnselectQuest()
                    mission?.let { missionId ->
                        navigateToSubmit(selectedQuest.id, mission.id, mission.type)
                    }
                }
            }
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        color = background
    ) {
        if (homeTabUiState is HomeTabUiState.Success) {
            val (userInfo, banners, popularQuests, recommendedQuests, largeRewardQuests, topRankUsers) = homeTabUiState.data
            LazyColumn {
                item {
                    HomeTabHeader(
                        profileImageId = userInfo.profileImageId,
                        myCommercialAreaName = userInfo.myCommercialAreaName,
                        isCommercialAreaName = userInfo.isCommericalAreaName,
                        onProfileClick = navigateToMyTab,
                        onMyZoneClick = onMyZoneClick,
                        onIsZoneClick = {
                            if (userInfo.isCommericalAreaName != null) {
                                showZoneSelectDisabledDialog = true
                            } else {
                                onIsZoneClick()
                            }
                        }
                    )
                }
                item {
                    BannerContent(
                        banners = banners,
                        onClick = onBannerClick
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    PopularQuestsContent(
                        popularQuests = popularQuests,
                        onPopularQuestClick = onSelectQuest,
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    RecommendedQuestsContent(
                        userNickname = userInfo.nickname,
                        recommendedQuests = recommendedQuests,
                        onRecommendedQuestClick = onSelectQuest,
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    LargeRewardQuestContent(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        largeRewardQuests = largeRewardQuests,
                        onQuestClick = onSelectQuest,
                        onMoreButtonClick = navigateToQuestTab
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    UserRankContent(
                        rankList = topRankUsers,
                        navigateToRankingTab = navigateToRankingTab,
                        navigateToProfile = navigateToProfile
                    )
                }
                item { Spacer(Modifier.height(84.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun HomeTabScreenPreview() {
    val homeTabUiState = HomeTabUiState.Success(
        HomeTabSuccessData(
            myInfo = MyInfoUiModel(
                nickname = "User",
                profileImageId = "default_profile",
                myCommercialAreaName = "서현역",
                isCommericalAreaName = "서현역"
            ),
            banners = listOf(
                Banner(
                    id = 1,
                    bannerImageId = "sample_banner_1.png",
                    description = "Sample Banner 1 Description",
                    navigationTitle = "Go to Sample 1",
                    title = "Sample Banner 1"
                ),
                Banner(
                    id = 2,
                    bannerImageId = "sample_banner_2.png",
                    description = "Sample Banner 2 Description",
                    navigationTitle = "Go to Sample 2",
                    title = "Sample Banner 2"
                )
            ),
            popularQuests = listOf(
                PopularQuest(
                    questId = 1,
                    expireDate = "2023-12-31",
                    imageId = "imageId1",
                    mainImageId = "mainImageId1",
                    questType = QuestType.Event,
                    title = "첫 번째 인기 퀘스트",
                    writerName = "작가 1"
                ),
                PopularQuest(
                    questId = 2,
                    expireDate = "2024-01-15",
                    imageId = "imageId2",
                    mainImageId = "mainImageId2",
                    questType = QuestType.Repeat.Daily,
                    title = "두 번째 인기 퀘스트 - 이것은 매우 긴 제목으로 두 줄을 넘을 수 있습니다. 확인해 보세요.",
                    writerName = "작가 2"
                ),
                PopularQuest(
                    questId = 3,
                    expireDate = "2024-02-28",
                    imageId = "imageId3",
                    mainImageId = "mainImageId3",
                    questType = QuestType.Normal,
                    title = "세 번째 인기 퀘스트",
                    writerName = "작가 3"
                ),
                PopularQuest(
                    questId = 4,
                    expireDate = "2024-03-10",
                    imageId = "imageId4",
                    mainImageId = "mainImageId4",
                    questType = QuestType.Event,
                    title = "네 번째 인기 퀘스트",
                    writerName = "작가 4"
                )
            ),
            recommendedQuests = List(8) {
                RecommendedQuest(
                    questId = 1,
                    imageId = "sample_image_id",
                    mainImageId = "sample_main_image_id",
                    title = "Sample Quest Title",
                    writerName = "Sample Writer"
                )
            },
            largeRewardQuests = listOf(
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
            ),
            topRankUsers = listOf(
                UserRank(
                    userId = "1",
                    nickName = "User 1",
                    point = 100,
                    profileImageId = "image1",
                    rank = 1,
                    title = Title(
                        name = "모두의 시선을 받는 자",
                        grade = TitleGrade.Legend,
                        type = TitleType.Metro
                    )
                ),
                UserRank(
                    userId = "2",
                    nickName = "User 2",
                    point = 90,
                    profileImageId = "image2",
                    rank = 2,
                    title = Title(
                        name = "모두의 시선을 받는 자자자자 ",
                        grade = TitleGrade.Rare,
                        type = TitleType.Commercial
                    )
                ),
                UserRank(
                    userId = "3",
                    nickName = "User 3",
                    point = 80,
                    profileImageId = "image3",
                    rank = 3,
                    title = Title(
                        name = "Title 3",
                        grade = TitleGrade.Standard,
                        type = TitleType.Contribution
                    )
                )
            )
        )
    )


    HomeTabScreen(
        homeTabUiState = homeTabUiState,
        selectedQuest = null,
        navigateToQuestTab = {},
        navigateToMyTab = {},
        navigateToSubmit = { _, _, _ -> },
        navigateToRankingTab = {},
        navigateToProfile = {},
        onBannerClick = {},
        onMyZoneClick = {},
        onIsZoneClick = {},
        onMissionImageClick = {},
        onSelectQuest = {},
        onUnselectQuest = {},
        onFavoriteClick = {}
    )
}