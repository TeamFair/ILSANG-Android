package com.ilsangtech.ilsang.feature.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.feature.banner.navigation.BannerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BannerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bannerDetailInfo = savedStateHandle.toRoute<BannerDetailRoute>()

    val uiState = flow {
        emit(
            BannerDetailUiState(
                id = bannerDetailInfo.id,
                title = bannerDetailInfo.title,
                description = bannerDetailInfo.description,
                imageId = bannerDetailInfo.imageId,
                bannerQuestUiState = BannerQuestUiState.Success(
                    onGoingQuests = emptyList(),
                    completedQuests = emptyList()
                )
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BannerDetailUiState(
            id = bannerDetailInfo.id,
            title = bannerDetailInfo.title,
            description = bannerDetailInfo.description,
            imageId = bannerDetailInfo.imageId,
            bannerQuestUiState = BannerQuestUiState.Loading
        )
    )
}