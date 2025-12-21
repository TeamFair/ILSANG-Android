package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.approval.component.ApprovalExampleCtaCard
import com.ilsangtech.ilsang.feature.approval.component.ApprovalExampleHeader
import com.ilsangtech.ilsang.feature.approval.component.ApprovalExampleItem
import com.ilsangtech.ilsang.feature.approval.model.ExampleMissionHistoryUiModel
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun ApprovalExampleScreen(
    viewModel: ApprovalExampleViewModel = hiltViewModel(),
    navigateToImageCapture: (Int, Int, Boolean) -> Unit,
    navigateToProfile: (String) -> Unit,
    navigateToReport: (Int) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val missionHistories = viewModel.exampleMissionHistories.collectAsLazyPagingItems()

    val missionId = viewModel.missionId
    val questId = viewModel.questId
    val isIsZoneQuest = viewModel.isIsZoneQuest

    val questTitle = viewModel.questTitle
    val questWriterName = viewModel.questWriterName
    val questType = viewModel.questType

    LaunchedEffect(Unit) {
        viewModel.missionHistoryRefreshTrigger.collect {
            missionHistories.refresh()
        }
    }
    ApprovalExampleScreen(
        questTitle = questTitle,
        questWriterName = questWriterName,
        questType = questType,
        onBackButtonClick = onBackButtonClick,
        missionHistories = missionHistories,
        onLikeButtonClick = viewModel::likeMissionHistory,
        onReportButtonClick = navigateToReport,
        onApproveButtonClick = { navigateToImageCapture(missionId, questId, isIsZoneQuest) },
        navigateToProfile = navigateToProfile
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ApprovalExampleScreen(
    questTitle: String,
    questWriterName: String,
    questType: QuestType,
    missionHistories: LazyPagingItems<ExampleMissionHistoryUiModel>,
    onLikeButtonClick: (ExampleMissionHistoryUiModel) -> Unit,
    onApproveButtonClick: () -> Unit,
    onReportButtonClick: (Int) -> Unit,
    navigateToProfile: (String) -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ApprovalExampleHeader(onBackButtonClick = onBackButtonClick)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                stickyHeader {
                    ApprovalExampleCtaCard(
                        questTitle = questTitle,
                        writerName = questWriterName,
                        questType = questType,
                        onClick = onApproveButtonClick
                    )
                }
                items(missionHistories.itemCount) {
                    missionHistories[it]?.let { missionHistory ->
                        ApprovalExampleItem(
                            uiModel = missionHistory,
                            onProfileClick = { navigateToProfile(missionHistory.user.userId) },
                            onLikeButtonClick = { onLikeButtonClick(missionHistory) },
                            onReportButtonClick = { onReportButtonClick(missionHistory.missionHistoryId) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApprovalExampleScreenPreview() {
    val missionHistories = flowOf(
        PagingData.from(
            listOf(
                ExampleMissionHistoryUiModel(
                    commercialAreaName = "강남",
                    createdAt = "2023.10.26 10:00",
                    currentUserEmojis = listOf(""),
                    hateCount = 0,
                    likeCount = 0,
                    missionHistoryId = 0,
                    submitImageId = "",
                    title = "강남역에서 사진 찍기",
                    user = MissionHistoryUser(
                        userId = "123",
                        nickname = "홍길동",
                        profileImageId = null,
                        title = null
                    ),
                    viewCount = 0,
                    shareCount = 0,
                    commentCount = 0
                )
            )
        )
    ).collectAsLazyPagingItems()

    ApprovalExampleScreen(
        questTitle = "정자동 최고의 돈까스 가게 가기",
        questWriterName = "야미돈까스 정자동점",
        questType = QuestType.Repeat.Weekly,
        missionHistories = missionHistories,
        onLikeButtonClick = {},
        onReportButtonClick = {},
        onApproveButtonClick = {},
        navigateToProfile = {},
        onBackButtonClick = {}
    )
}