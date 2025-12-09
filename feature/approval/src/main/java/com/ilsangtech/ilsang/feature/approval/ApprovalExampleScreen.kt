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
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.approval.component.ApprovalExampleHeader
import com.ilsangtech.ilsang.feature.approval.component.ApprovalExampleItem
import com.ilsangtech.ilsang.feature.approval.model.ExampleMissionHistoryUiModel
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun ApprovalExampleScreen(
    viewModel: ApprovalExampleViewModel = hiltViewModel(),
    navigateToProfile: (String) -> Unit,
    navigateToReport: (Int) -> Unit,
    onBackButtonClick: () -> Unit
) {
    val missionHistories = viewModel.exampleMissionHistories.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.missionHistoryRefreshTrigger.collect {
            missionHistories.refresh()
        }
    }
    ApprovalExampleScreen(
        onBackButtonClick = onBackButtonClick,
        missionHistories = missionHistories,
        onLikeButtonClick = viewModel::likeMissionHistory,
        onReportButtonClick = navigateToReport,
        navigateToProfile = navigateToProfile
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ApprovalExampleScreen(
    missionHistories: LazyPagingItems<ExampleMissionHistoryUiModel>,
    onLikeButtonClick: (ExampleMissionHistoryUiModel) -> Unit,
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
        missionHistories = missionHistories,
        onLikeButtonClick = {},
        onReportButtonClick = {},
        navigateToProfile = {},
        onBackButtonClick = {}
    )
}