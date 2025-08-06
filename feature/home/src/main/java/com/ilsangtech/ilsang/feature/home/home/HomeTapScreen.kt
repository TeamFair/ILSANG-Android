package com.ilsangtech.ilsang.feature.home.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ilsangtech.ilsang.core.model.Banner
import com.ilsangtech.ilsang.core.ui.quest.QuestBottomSheet
import com.ilsangtech.ilsang.core.util.FileManager
import com.ilsangtech.ilsang.feature.home.BuildConfig
import com.ilsangtech.ilsang.feature.home.HomeViewModel
import com.ilsangtech.ilsang.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTapScreen(
    userNickname: String?,
    homeViewModel: HomeViewModel,
    navigateToQuestTab: () -> Unit,
    navigateToMyTab: () -> Unit,
    navigateToSubmit: () -> Unit,
    navigateToRankingTab: () -> Unit,
    navigateToProfile: (String) -> Unit
) {
    val context = LocalContext.current
    val selectedQuest by homeViewModel.selectedQuest.collectAsStateWithLifecycle()
    val homeTabUiState by homeViewModel.homeTapUiState.collectAsStateWithLifecycle()
    val userInfo by homeViewModel.myInfo.collectAsStateWithLifecycle()
    val capturedImageFile by homeViewModel.capturedImageFile.collectAsStateWithLifecycle()
    val capturedImageUri =
        remember(capturedImageFile) { FileManager.getUriForFile(capturedImageFile, context) }

    val imageCaptureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                navigateToSubmit()
            }
        }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (selectedQuest != null) {
        selectedQuest?.let { quest ->
            QuestBottomSheet(
                quest = quest,
                bottomSheetState = bottomSheetState,
                onDismiss = homeViewModel::unselectQuest,
                onFavoriteClick = homeViewModel::updateQuestFavoriteStatus,
                onApproveButtonClick = { imageCaptureLauncher.launch(capturedImageUri) }
            )
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        var bottomSheetQuest by remember { mutableStateOf<Quest?>(null) }
        var showBottomSheet by remember { mutableStateOf(false) }

        if (showBottomSheet) {
            bottomSheetQuest?.let {
                QuestBottomSheet(
                    quest = it,
                    showBottomSheet = showBottomSheet,
                    onDismiss = {
                        showBottomSheet = false
                        bottomSheetQuest = null
                    },
                    onApproveButtonClick = {
                        imageCaptureLauncher.launch(capturedImageUri)
                    }
                )
            }
        }

        if (homeTabUiState is HomeTapUiState.Success) {
            LazyColumn {
                item {
                    HomeTapTopBar(
                        imageId = userInfo?.profileImage,
                        onClickProfile = navigateToMyTab
                    )
                }
                item {
                    BannerView(
                        banners = (homeTabUiState as HomeTapUiState.Success).data.banners,
                        onClick = navigateToQuestTab
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    PopularQuestsContent(
                        popularQuests = (homeTabUiState as HomeTapUiState.Success).data.popularQuests,
                        onPopularQuestClick = {
                            bottomSheetQuest = it
                            showBottomSheet = true
                            onApproveButtonClick(it)
                        }
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    RecommendedQuestsContent(
                        userNickname = userNickname,
                        recommendedQuests = (homeTabUiState as HomeTapUiState.Success).data.recommendedQuests,
                        onRecommendedQuestClick = {
                            bottomSheetQuest = it
                            showBottomSheet = true
                            onApproveButtonClick(it)
                        }
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    LargeRewardQuestsContent(
                        largeRewardQuests = (homeTabUiState as HomeTapUiState.Success).data.largeRewardQuests,
                        navigateToQuestTab = navigateToQuestTab
                    )
                }
                item { Spacer(Modifier.height(36.dp)) }
                item {
                    UserRankContent(
                        rankList = (homeTabUiState as HomeTapUiState.Success).data.topRankUsers,
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
fun HomeTapTopBar(
    modifier: Modifier = Modifier,
    imageId: String?,
    onClickProfile: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .statusBarsPadding()
            .padding(
                top = 8.dp,
                bottom = 16.dp
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.home_ilsang_logo),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(Modifier.weight(1f))
        AsyncImage(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .clickable(
                    onClick = onClickProfile,
                    indication = null,
                    interactionSource = null
                ),
            model = BuildConfig.IMAGE_URL + imageId,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.default_user_profile),
            contentDescription = null
        )
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
        userNickname = "누구누구",
        homeViewModel = hiltViewModel(),
        navigateToQuestTab = {},
        navigateToMyTab = {},
        navigateToSubmit = {},
        navigateToRankingTab = {},
        navigateToProfile = {},
    )
}