package com.ilsangtech.ilsang.feature.banner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ilsangtech.ilsang.designsystem.theme.background
import com.ilsangtech.ilsang.feature.banner.component.BannerDetailHeader
import com.ilsangtech.ilsang.feature.banner.component.bannerDetailInfoContent

@Composable
internal fun BannerDetailScreen(
    bannerDetailViewModel: BannerDetailViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {
    val bannerDetailUiState by bannerDetailViewModel.uiState.collectAsStateWithLifecycle()
    BannerDetailScreen(
        bannerDetailUiState = bannerDetailUiState,
        onBackButtonClick = onBackButtonClick
    )
}

@Composable
private fun BannerDetailScreen(
    bannerDetailUiState: BannerDetailUiState,
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerDetailScreenPreview() {
    val bannerDetailUiState = BannerDetailUiState(
        id = 1,
        imageId = "your_image_id",
        title = "*일상 X 미트스팟 한정 퀘스트 이벤트*",
        description = "이벤트에 대한 설명이 들어갑니다 이벤트에 대한 설명이 들어갑니다",
        bannerQuestUiState = BannerQuestUiState.Loading
    )
    BannerDetailScreen(
        bannerDetailUiState = bannerDetailUiState,
        onBackButtonClick = {}
    )
}