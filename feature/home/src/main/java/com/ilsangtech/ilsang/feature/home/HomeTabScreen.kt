package com.ilsangtech.ilsang.feature.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.banner.Banner
import com.ilsangtech.ilsang.core.ui.quest.bottomsheet.QuestBottomSheet
import com.ilsangtech.ilsang.feature.home.component.BannerContent
import com.ilsangtech.ilsang.feature.home.component.HomeTabHeader
import com.ilsangtech.ilsang.feature.home.component.LargeRewardQuestContent
import com.ilsangtech.ilsang.feature.home.component.PopularQuestsContent
import com.ilsangtech.ilsang.feature.home.component.RecommendedQuestsContent
import com.ilsangtech.ilsang.feature.home.component.UserRankContent
import com.ilsangtech.ilsang.feature.home.model.HomeTabUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTabScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToQuestTab: () -> Unit,
    navigateToMyTab: () -> Unit,
    navigateToSubmit: (String) -> Unit,
    navigateToRankingTab: () -> Unit,
    navigateToProfile: (String) -> Unit,
    onBannerClick: (Banner) -> Unit,
    onMyZoneClick: () -> Unit,
    onIsZoneClick: () -> Unit
) {
    val selectedQuest by homeViewModel.selectedQuest.collectAsStateWithLifecycle()
    val homeTabUiState by homeViewModel.homeTabUiState.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (selectedQuest != null) {
        selectedQuest?.let { quest ->
            QuestBottomSheet(
                quest = quest,
                bottomSheetState = bottomSheetState,
                onDismiss = homeViewModel::unselectQuest,
                onFavoriteClick = homeViewModel::updateQuestFavoriteStatus,
                onMissionImageClick = {
                    //TODO 퀘스트 인증 예시 화면으로 이동
                },
                onApproveButtonClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        homeViewModel.unselectQuest()
                        //TODO 제출 화면으로 이동 구현
                    }
                }
            )
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        if (homeTabUiState is HomeTabUiState.Success) {
            val (userInfo, banners, popularQuests, recommendedQuests, largeRewardQuests, topRankUsers) =
                (homeTabUiState as HomeTabUiState.Success).data
            LazyColumn {
                item {
                    HomeTabHeader(
                        profileImageId = userInfo.profileImageId,
                        metroName = null,
                        areaName = null,
                        onProfileClick = navigateToMyTab,
                        onMyZoneClick = onMyZoneClick,
                        onIsZoneClick = onIsZoneClick
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
                        onPopularQuestClick = homeViewModel::selectQuest,
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    RecommendedQuestsContent(
                        userNickname = userInfo.nickname,
                        recommendedQuests = recommendedQuests,
                        onRecommendedQuestClick = homeViewModel::selectQuest
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    LargeRewardQuestContent(
                        largeRewardQuests = largeRewardQuests,
                        onQuestClick = homeViewModel::selectQuest,
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

@Preview
@Composable
private fun HomeTabScreenPreview() {
    HomeTabScreen(
        homeViewModel = hiltViewModel(),
        navigateToQuestTab = {},
        navigateToMyTab = {},
        navigateToSubmit = {},
        navigateToRankingTab = {},
        navigateToProfile = {},
        onBannerClick = {},
        onMyZoneClick = {},
        onIsZoneClick = {}
    )
}