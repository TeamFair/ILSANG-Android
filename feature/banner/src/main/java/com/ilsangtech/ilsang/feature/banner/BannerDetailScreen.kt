package com.ilsangtech.ilsang.feature.banner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.QuestDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.reward.RewardPoint
import com.ilsangtech.ilsang.core.ui.quest.bottomsheet.QuestBottomSheet
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.banner.component.BannerDetailHeader
import com.ilsangtech.ilsang.feature.banner.component.bannerDetailInfoContent
import com.ilsangtech.ilsang.feature.banner.component.bannerDetailQuestsContent
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
internal fun BannerDetailScreen(
    bannerDetailViewModel: BannerDetailViewModel = hiltViewModel(),
    navigateToSubmit: (Int, Int, String) -> Unit,
    navigateToMissionExample: (Int) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val bannerDetailInfo = bannerDetailViewModel.bannerDetailInfo

    val selectedQuestType by bannerDetailViewModel.selectedQuestType.collectAsStateWithLifecycle()
    val selectedSortType by bannerDetailViewModel.selectedSortType.collectAsStateWithLifecycle()
    val selectedQuest by bannerDetailViewModel.selectedQuest.collectAsStateWithLifecycle()

    val onGoingQuests = bannerDetailViewModel.onGoingQuests.collectAsLazyPagingItems()
    val completedQuests = bannerDetailViewModel.completedQuests.collectAsLazyPagingItems()

    BannerDetailScreen(
        imageId = bannerDetailInfo.imageId,
        title = bannerDetailInfo.title,
        description = bannerDetailInfo.description,
        selectedQuest = selectedQuest,
        selectedQuestType = selectedQuestType,
        selectedSortType = selectedSortType,
        onGoingQuests = onGoingQuests,
        completedQuests = completedQuests,
        onQuestClick = bannerDetailViewModel::selectQuest,
        onUnselectQuest = bannerDetailViewModel::unselectQuest,
        onQuestTypeChanged = bannerDetailViewModel::onQuestTypeChanged,
        onSortTypeChanged = bannerDetailViewModel::onSortTypeChanged,
        onBackButtonClick = onBackButtonClick,
        onFavoriteClick = bannerDetailViewModel::updateQuestFavoriteStatus,
        onMissionImageClick = navigateToMissionExample,
        onSubmitButtonClick = navigateToSubmit
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BannerDetailScreen(
    imageId: String,
    title: String,
    description: String,
    selectedQuest: QuestDetail?,
    selectedQuestType: BannerDetailQuestType,
    selectedSortType: BannerDetailSortType,
    onGoingQuests: LazyPagingItems<BannerQuest>,
    completedQuests: LazyPagingItems<BannerQuest>,
    onQuestTypeChanged: (BannerDetailQuestType) -> Unit,
    onSortTypeChanged: (BannerDetailSortType) -> Unit,
    onQuestClick: (BannerQuest) -> Unit,
    onUnselectQuest: () -> Unit,
    onFavoriteClick: () -> Unit,
    onMissionImageClick: (Int) -> Unit,
    onSubmitButtonClick: (Int, Int, String) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (selectedQuest != null) {
        QuestBottomSheet(
            quest = selectedQuest,
            bottomSheetState = bottomSheetState,
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
                        onSubmitButtonClick(selectedQuest.id, mission.id, mission.type)
                    }
                }
            },
            onDismiss = onUnselectQuest
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            BannerDetailHeader(
                bannerTitle = title,
                onBackButtonClick = onBackButtonClick
            )
            LazyColumn(
                modifier = Modifier.navigationBarsPadding(),
                contentPadding = PaddingValues(bottom = 72.dp)
            ) {
                bannerDetailInfoContent(
                    imageId = imageId,
                    title = title,
                    description = description
                )
                bannerDetailQuestsContent(
                    onGoingQuests = onGoingQuests,
                    completedQuests = completedQuests,
                    selectedQuestType = selectedQuestType,
                    selectedSortType = selectedSortType,
                    onQuestClick = onQuestClick,
                    onQuestTypeChanged = onQuestTypeChanged,
                    onSortTypeChanged = onSortTypeChanged
                )
            }
        }
    }
}

@Preview
@Composable
private fun BannerDetailScreenPreview() {
    val onGoingQuests = flowOf(
        PagingData.from(
            listOf(
                BannerQuest(
                    questId = 1,
                    questType = QuestType.Event,
                    expireDate = "2023-12-31",
                    imageId = "sample_image_1",
                    mainImageId = "sample_main_image_1",
                    rewards = listOf(
                        RewardPoint.Metro(5),
                        RewardPoint.Commercial(10),
                        RewardPoint.Contribute(15)
                    ),
                    title = "Sample OnGoing Quest 1",
                    writerName = "Writer A"
                ),
                BannerQuest(
                    questId = 2,
                    questType = QuestType.Normal,
                    expireDate = "2024-01-15",
                    imageId = "sample_image_2",
                    mainImageId = "sample_main_image_2",
                    rewards = listOf(
                        RewardPoint.Metro(5),
                        RewardPoint.Commercial(10),
                        RewardPoint.Contribute(15)
                    ),
                    title = "Sample OnGoing Quest 2",
                    writerName = "Writer B"
                )
            )
        )
    ).collectAsLazyPagingItems()

    val completedQuests = flowOf(
        PagingData.from(
            listOf(
                BannerQuest(
                    questId = 3,
                    questType = QuestType.Event,
                    expireDate = "2023-11-30",
                    imageId = "sample_image_3",
                    mainImageId = "sample_main_image_3",
                    rewards = listOf(
                        RewardPoint.Metro(5),
                        RewardPoint.Commercial(10),
                        RewardPoint.Contribute(15)
                    ),
                    title = "Sample Completed Quest 1",
                    writerName = "Writer C"
                )
            )
        )
    ).collectAsLazyPagingItems()

    BannerDetailScreen(
        imageId = "sample_banner_image",
        title = "Sample Banner Title",
        description = "This is a sample banner description. It can be a long text explaining the details of the banner.",
        selectedQuest = null,
        selectedQuestType = BannerDetailQuestType.OnGoing,
        selectedSortType = BannerDetailSortType.Popular,
        onGoingQuests = onGoingQuests,
        completedQuests = completedQuests,
        onQuestClick = {},
        onQuestTypeChanged = {},
        onSortTypeChanged = {},
        onSubmitButtonClick = { _, _, _ -> },
        onBackButtonClick = {},
        onUnselectQuest = {},
        onFavoriteClick = {},
        onMissionImageClick = {}
    )
}