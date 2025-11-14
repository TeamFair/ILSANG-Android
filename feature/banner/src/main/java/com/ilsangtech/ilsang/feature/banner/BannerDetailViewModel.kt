package com.ilsangtech.ilsang.feature.banner

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.quest.BannerQuest
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.ui.quest.model.BannerQuestUiModel
import com.ilsangtech.ilsang.core.ui.quest.model.toUiModel
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userRepository: UserRepository,
    private val questRepository: QuestRepository,
    questCompleteDateRepository: QuestCompleteDateRepository
) : ViewModel() {
    val bannerDetailInfo = savedStateHandle.toRoute<BannerDetailRoute>()

    private val _selectedQuestType = MutableStateFlow(BannerDetailQuestType.OnGoing)
    val selectedQuestType = _selectedQuestType.asStateFlow()

    private val _selectedSortType = MutableStateFlow(BannerDetailSortType.ExpiredDate)
    val selectedSortType = _selectedSortType.asStateFlow()

    private val _selectedQuest = MutableStateFlow<BannerQuestUiModel?>(null)

    private val questDetailRefresh = MutableSharedFlow<Unit>(replay = 1)

    private val _myInfo = userRepository.getMyInfo()

    private val questCompleteDateMapFlow = questCompleteDateRepository.questCompleteDateMapFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedQuestDetail = combine(
        _selectedQuest,
        _myInfo,
        questDetailRefresh.onStart { emit(Unit) }
    ) { quest, myInfo, _ -> quest to myInfo }.flatMapLatest { (quest, myInfo) ->
        quest?.let {
            questRepository.getQuestDetail(
                questId = it.questId,
                isIsZoneQuest = it.isIsZoneQuest
            ).map { questDetail ->
                questDetail.toUiModel(quest.remainHours)
            }
        } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val onGoingQuests = combine(_myInfo, selectedSortType) { myInfo, sortType ->
        myInfo.isCommercialAreaCode to sortType
    }.flatMapLatest { (isZoneCode, sortType) ->
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
            orderRewardDesc = orderRewardDesc,
            isZoneCode = isZoneCode
        )
    }.cachedIn(viewModelScope)
        .transformLatest { pagingData ->
            questCompleteDateMapFlow.collect { dateMap ->
                emit(
                    pagingData.filter { quest ->
                        (quest.lastCompleteDate == null && quest.questId !in dateMap)
                                || quest.questType is QuestType.Repeat
                    }.map { quest ->
                        if (quest.questId in dateMap) quest.copy(
                            lastCompleteDate = dateMap[quest.questId]
                        ) else quest
                    }.map(BannerQuest::toUiModel)
                )
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val completedQuests = combine(_myInfo, selectedSortType) { myInfo, sortType ->
        myInfo.isCommercialAreaCode to sortType
    }.flatMapLatest { (isZoneCode, sortType) ->
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
            orderRewardDesc = orderRewardDesc,
            isZoneCode = isZoneCode
        )
    }.map { pagingData ->
        pagingData.map(BannerQuest::toUiModel)
    }

    fun onQuestTypeChanged(questType: BannerDetailQuestType) {
        _selectedQuestType.update { questType }
    }

    fun onSortTypeChanged(sortType: BannerDetailSortType) {
        _selectedSortType.update { sortType }
    }

    fun selectQuest(quest: BannerQuestUiModel) {
        viewModelScope.launch {
            _selectedQuest.update { quest }
        }
    }

    fun unselectQuest() {
        _selectedQuest.update { null }
    }

    fun updateQuestFavoriteStatus() {
        viewModelScope.launch {
            selectedQuestDetail.value?.let { quest ->
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