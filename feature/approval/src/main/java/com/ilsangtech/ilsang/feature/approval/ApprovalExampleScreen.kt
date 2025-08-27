package com.ilsangtech.ilsang.feature.approval

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.ilsangtech.ilsang.feature.approval.component.ApprovalItem
import com.ilsangtech.ilsang.feature.approval.model.MissionHistoryUiModel
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun ApprovalExampleScreen(
    viewModel: ApprovalExampleViewModel = hiltViewModel(),
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
        onHateButtonClick = viewModel::hateMissionHistory,
        onReportButtonClick = viewModel::reportMissionHistory,
        navigateToProfile = {}
    )
}

@Composable
private fun ApprovalExampleScreen(
    missionHistories: LazyPagingItems<MissionHistoryUiModel>,
    onLikeButtonClick: (MissionHistoryUiModel) -> Unit,
    onHateButtonClick: (MissionHistoryUiModel) -> Unit,
    onReportButtonClick: (MissionHistoryUiModel) -> Unit,
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
                contentPadding = PaddingValues(vertical = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { Spacer(Modifier.statusBarsPadding()) }
                items(missionHistories.itemCount) {
                    missionHistories[it]?.let { missionHistory ->
                        ApprovalItem(
                            missionHistory = missionHistory,
                            onProfileClick = { navigateToProfile(missionHistory.user.userId) },
                            onLikeButtonClick = { onLikeButtonClick(missionHistory) },
                            onHateButtonClick = { onHateButtonClick(missionHistory) },
                            onReportButtonClick = { onReportButtonClick(missionHistory) }
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
                MissionHistoryUiModel(
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
                    viewCount = 0
                )
            )
        )
    ).collectAsLazyPagingItems()

    ApprovalExampleScreen(
        missionHistories = missionHistories,
        onLikeButtonClick = {},
        onHateButtonClick = {},
        onReportButtonClick = {},
        navigateToProfile = {},
        onBackButtonClick = {}
    )
}