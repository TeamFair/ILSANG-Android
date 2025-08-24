package com.ilsangtech.ilsang.feature.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.feature.banner.navigation.BannerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BannerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository
) : ViewModel() {
    val bannerDetailInfo = savedStateHandle.toRoute<BannerDetailRoute>()

    private val _selectedQuestType = MutableStateFlow(BannerDetailQuestType.OnGoing)
    val selectedQuestType = _selectedQuestType.asStateFlow()

    private val _selectedSortType = MutableStateFlow(BannerDetailSortType.ExpiredDate)
    val selectedSortType = _selectedSortType.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val onGoingQuests = selectedSortType.flatMapLatest { sortType ->
        val orderExpiredDesc = if (sortType == BannerDetailSortType.ExpiredDate) true else null
        val orderRewardDesc = when (sortType) {
            BannerDetailSortType.PointDesc -> true
            BannerDetailSortType.PointAsc -> false
            else -> null
        }
        questRepository.getBannerQuests(
            bannerId = bannerDetailInfo.id,
            completedYn = false,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc
        )
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val completedQuests = selectedSortType.flatMapLatest { sortType ->
        val orderExpiredDesc = if (sortType == BannerDetailSortType.ExpiredDate) true else null
        val orderRewardDesc = when (sortType) {
            BannerDetailSortType.PointDesc -> true
            BannerDetailSortType.PointAsc -> false
            else -> null
        }
        questRepository.getBannerQuests(
            bannerId = bannerDetailInfo.id,
            completedYn = true,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc
        )
    }.cachedIn(viewModelScope)

    fun onQuestTypeChanged(questType: BannerDetailQuestType) {
        _selectedQuestType.update { questType }
    }

    fun onSortTypeChanged(sortType: BannerDetailSortType) {
        _selectedSortType.update { sortType }
    }
}