package com.ilsangtech.ilsang.feature.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.feature.banner.navigation.BannerDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private val selectedQuestId = MutableStateFlow<Int?>(null)
    private val questDetailRefresh = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedQuest = combine(
        selectedQuestId,
        questDetailRefresh.onStart { emit(Unit) }
    ) { questId, _ -> questId }.flatMapLatest { questId ->
        questId?.let {
            questRepository.getQuestDetail(questId)
        } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

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

    fun selectQuest(quest: BannerQuest) {
        viewModelScope.launch {
            selectedQuestId.update { quest.questId }
        }
    }

    fun unselectQuest() {
        selectedQuestId.update { null }
    }

    fun updateQuestFavoriteStatus() {
        viewModelScope.launch {
            selectedQuest.value?.let { quest ->
                val result = if (quest.favoriteYn) {
                    questRepository.deleteFavoriteQuest(quest.id)
                } else {
                    questRepository.registerFavoriteQuest(quest.id)
                }
                result.onSuccess { questDetailRefresh.emit(Unit) }
            }
        }
    }
}