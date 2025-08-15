package com.ilsangtech.ilsang.feature.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.feature.banner.navigation.BannerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BannerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bannerDetailInfo = savedStateHandle.toRoute<BannerDetailRoute>()

    private val _selectedQuestType = MutableStateFlow(BannerDetailQuestType.OnGoing)
    private val selectedQuestType = _selectedQuestType.asStateFlow()

    private val _selectedSortType = MutableStateFlow(BannerDetailSortType.ExpiredDate)
    private val selectedSortType = _selectedSortType.asStateFlow()

    val uiState = selectedSortType.map { sortType ->
        //TODO 배너 퀘스트 API 적용
        val onGoingQuests = emptyList<BannerQuest>()
        val completedQuests = emptyList<BannerQuest>()
        Triple(sortType, onGoingQuests, completedQuests)
    }.combine(selectedQuestType) { (sortType, onGoingQuests, completedQuests), questType ->
        BannerDetailUiState(
            id = bannerDetailInfo.id,
            title = bannerDetailInfo.title,
            description = bannerDetailInfo.description,
            imageId = bannerDetailInfo.imageId,
            selectedQuestType = questType,
            selectedSortType = sortType,
            bannerQuestUiState = BannerQuestUiState.Success(
                onGoingQuests = onGoingQuests,
                completedQuests = completedQuests
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
            selectedQuestType = BannerDetailQuestType.OnGoing,
            selectedSortType = BannerDetailSortType.ExpiredDate,
            bannerQuestUiState = BannerQuestUiState.Loading
        )
    )

    fun onQuestTypeChanged(questType: BannerDetailQuestType) {
        _selectedQuestType.update { questType }
    }

    fun onSortTypeChanged(sortType: BannerDetailSortType) {
        _selectedSortType.update { sortType }
    }
}