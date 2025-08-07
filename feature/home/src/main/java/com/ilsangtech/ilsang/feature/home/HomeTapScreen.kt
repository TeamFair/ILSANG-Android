package com.ilsangtech.ilsang.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Banner
import com.ilsangtech.ilsang.core.ui.quest.bottomsheet.QuestBottomSheet
import com.ilsangtech.ilsang.feature.home.component.HomeTabHeader
import com.ilsangtech.ilsang.feature.home.component.LargeRewardQuestsContent
import com.ilsangtech.ilsang.feature.home.component.PopularQuestsContent
import com.ilsangtech.ilsang.feature.home.component.RecommendedQuestsContent
import com.ilsangtech.ilsang.feature.home.component.UserRankContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTapScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToQuestTab: () -> Unit,
    navigateToMyTab: () -> Unit,
    navigateToSubmit: (String) -> Unit,
    navigateToRankingTab: () -> Unit,
    navigateToProfile: (String) -> Unit,
    onMyZoneClick: () -> Unit
) {
    val selectedQuest by homeViewModel.selectedQuest.collectAsStateWithLifecycle()
    val homeTabUiState by homeViewModel.homeTapUiState.collectAsStateWithLifecycle()
    val userInfo by homeViewModel.myInfo.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (selectedQuest != null) {
        selectedQuest?.let { quest ->
            QuestBottomSheet(
                quest = quest,
                bottomSheetState = bottomSheetState,
                onDismiss = homeViewModel::unselectQuest,
                onFavoriteClick = homeViewModel::updateQuestFavoriteStatus,
                onApproveButtonClick = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        homeViewModel.unselectQuest()
                        navigateToSubmit(quest.questId)
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
        if (homeTabUiState is HomeTapUiState.Success) {
            val (banners, popularQuests, recommendedQuests, largeRewardQuests, topRankUsers) =
                (homeTabUiState as HomeTapUiState.Success).data
            LazyColumn {
                item {
                    HomeTabHeader(
                        profileImageId = userInfo?.profileImage,
                        metroName = null,
                        areaName = null,
                        onProfileClick = navigateToMyTab,
                        onMyZoneClick = onMyZoneClick
                    )
                }
                item {
                    BannerView(
                        banners = banners,
                        onClick = navigateToQuestTab
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    PopularQuestsContent(
                        popularQuests = popularQuests,
                        onPopularQuestClick = homeViewModel::selectQuest
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    RecommendedQuestsContent(
                        userNickname = userInfo?.nickname,
                        recommendedQuests = recommendedQuests,
                        onRecommendedQuestClick = homeViewModel::selectQuest
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    LargeRewardQuestsContent(
                        largeRewardQuests = largeRewardQuests,
                        navigateToQuestTab = navigateToQuestTab
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

@Composable
fun BannerView(
    banners: List<Banner>,
    onClick: () -> Unit
) {
    val pagerState = rememberPagerState { banners.size }
    HorizontalPager(
        state = pagerState
    ) { page ->
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(11 / 10f)
                .clip(RoundedCornerShape(12.dp))
                .clickable(
                    onClick = {
                        if (banners[page].description.contains("quest")) {
                            onClick()
                        }
                    },
                    indication = null,
                    interactionSource = null
                ),
            contentScale = ContentScale.Crop,
            model = BuildConfig.IMAGE_URL + banners[page].imageId,
            contentDescription = banners[page].description
        )
    }
}

@Preview
@Composable
private fun HomeTapScreenPreview() {
    HomeTapScreen(
        homeViewModel = hiltViewModel(),
        navigateToQuestTab = {},
        navigateToMyTab = {},
        navigateToSubmit = {},
        navigateToRankingTab = {},
        navigateToProfile = {},
        onMyZoneClick = {}
    )
}