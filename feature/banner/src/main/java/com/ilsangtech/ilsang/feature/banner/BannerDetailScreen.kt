package com.ilsangtech.ilsang.feature.banner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.core.model.RewardPoint
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.banner.component.BannerDetailHeader
import com.ilsangtech.ilsang.feature.banner.component.bannerDetailInfoContent
import com.ilsangtech.ilsang.feature.banner.component.bannerDetailQuestsContent

@Composable
internal fun BannerDetailScreen(
    bannerDetailViewModel: BannerDetailViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val bannerDetailUiState by bannerDetailViewModel.uiState.collectAsStateWithLifecycle()
    BannerDetailScreen(
        bannerDetailUiState = bannerDetailUiState,
        onQuestClick = {},
        onQuestTypeChanged = bannerDetailViewModel::onQuestTypeChanged,
        onSortTypeChanged = bannerDetailViewModel::onSortTypeChanged,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun BannerDetailScreen(
    bannerDetailUiState: BannerDetailUiState,
    onQuestClick: (BannerQuest) -> Unit,
    onQuestTypeChanged: (BannerDetailQuestType) -> Unit,
    onSortTypeChanged: (BannerDetailSortType) -> Unit,
    onBackButtonClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = background
    ) {
        Column {
            BannerDetailHeader(
                bannerTitle = bannerDetailUiState.title,
                onBackButtonClick = onBackButtonClick
            )
            LazyColumn {
                bannerDetailInfoContent(
                    imageId = bannerDetailUiState.imageId,
                    title = bannerDetailUiState.title,
                    description = bannerDetailUiState.description
                )
                bannerDetailQuestsContent(
                    bannerQuestUiState = bannerDetailUiState.bannerQuestUiState,
                    selectedQuestType = bannerDetailUiState.selectedQuestType,
                    selectedSortType = bannerDetailUiState.selectedSortType,
                    onQuestClick = onQuestClick,
                    onQuestTypeChanged = onQuestTypeChanged,
                    onSortTypeChanged = onSortTypeChanged
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerDetailScreenPreview() {
    val previewBannerQuest = BannerQuest(
        questId = 1,
        expireDate = "",
        imageId = "",
        mainImageId = "",
        rewards = listOf(
            RewardPoint.Metro(5),
            RewardPoint.Commercial(10),
            RewardPoint.Contribute(15)
        ),
        title = "예시 퀘스트 타이틀",
        writerName = "예시 퀘스트 작성자"
    )

    var selectedQuestType by remember { mutableStateOf(BannerDetailQuestType.OnGoing) }
    var selectedSortType by remember { mutableStateOf(BannerDetailSortType.ExpiredDate) }
    val bannerDetailUiState = BannerDetailUiState(
        id = 1,
        imageId = "your_image_id",
        title = "*일상 X 미트스팟 한정 퀘스트 이벤트*",
        description = "이벤트에 대한 설명이 들어갑니다 이벤트에 대한 설명이 들어갑니다",
        selectedQuestType = selectedQuestType,
        selectedSortType = selectedSortType,
        bannerQuestUiState = BannerQuestUiState.Success(
            onGoingQuests = List(10) { previewBannerQuest },
            completedQuests = List(10) { previewBannerQuest }
        )
    )
    BannerDetailScreen(
        bannerDetailUiState = bannerDetailUiState,
        onQuestClick = {},
        onQuestTypeChanged = { selectedQuestType = it },
        onSortTypeChanged = { selectedSortType = it },
        onBackButtonClick = {}
    )
}