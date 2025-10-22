package com.ilsangtech.ilsang.feature.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel
import com.ilsangtech.ilsang.feature.quest.model.RepeatQuestTypeUiModel
import com.ilsangtech.ilsang.feature.quest.model.SortTypeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestTabViewModel @Inject constructor(
    userRepository: UserRepository,
    areaRepository: AreaRepository,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val questDetailRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    private val _selectedQuestTab = MutableStateFlow(QuestTabUiModel.NORMAL)
    val selectedQuestTab = _selectedQuestTab.asStateFlow()

    private val _selectedRepeatType = MutableStateFlow<RepeatQuestTypeUiModel?>(null)
    val selectedRepeatType = _selectedRepeatType.asStateFlow()

    private val _selectedSortType = MutableStateFlow(SortTypeUiModel.PointDesc)
    val selectedSortType = _selectedSortType.asStateFlow()

    private val selectedQuestId = MutableStateFlow<Int?>(null)

    private val myInfo = userRepository.getMyInfo().shareIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        replay = 1
    )

    val areaName = myInfo.map { myInfo ->
        areaRepository.getCommercialArea(
            commercialAreaCode = myInfo.myCommericalAreaCode
        ).areaName
    }.catch {
        emit("")
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val favoriteQuestSet = MutableStateFlow(setOf<Int>())
    private val unfavoriteQuests = MutableStateFlow(setOf<Int>())

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedQuest = combine(
        selectedQuestId,
        questDetailRefreshTrigger.onStart { emit(Unit) }
    ) { questId, _ -> questId }.flatMapLatest { questId ->
        questId?.let { questRepository.getQuestDetail(questId) } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val normalQuests = myInfo.flatMapLatest { myInfo ->
        val areaCode = myInfo.myCommericalAreaCode
        val isZoneCode = myInfo.isCommercialAreaCode
        selectedSortType.flatMapLatest {
            questRepository.getTypedQuests(
                questType = QuestType.Normal,
                commercialAreaCode = areaCode,
                isZoneCode = isZoneCode,
                orderRewardDesc = when (it) {
                    SortTypeUiModel.PointDesc -> true
                    SortTypeUiModel.PointAsc -> false
                    else -> null
                }
            )
        }
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val repeatQuests = myInfo.flatMapLatest { myInfo ->
        val areaCode = myInfo.myCommericalAreaCode
        val isZoneCode = myInfo.isCommercialAreaCode
        combine(
            selectedSortType, selectedRepeatType
        ) { sortType, repeatType ->
            sortType to repeatType
        }.flatMapLatest { (sortType, repeatType) ->
            val questType = when (repeatType) {
                RepeatQuestTypeUiModel.Daily -> QuestType.Repeat.Daily
                RepeatQuestTypeUiModel.Weekly -> QuestType.Repeat.Weekly
                RepeatQuestTypeUiModel.Monthly -> QuestType.Repeat.Monthly
                else -> null
            }
            questRepository.getTypedQuests(
                questType = questType,
                commercialAreaCode = areaCode,
                isZoneCode = isZoneCode,
                orderRewardDesc = when (sortType) {
                    SortTypeUiModel.PointDesc -> true
                    SortTypeUiModel.PointAsc -> false
                    else -> null
                }
            )
        }
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val eventQuests = myInfo.flatMapLatest { myInfo ->
        val areaCode = myInfo.myCommericalAreaCode
        val isZoneCode = myInfo.isCommercialAreaCode
        selectedSortType.flatMapLatest {
            questRepository.getTypedQuests(
                questType = QuestType.Event,
                commercialAreaCode = areaCode,
                isZoneCode = isZoneCode,
                orderRewardDesc = when (it) {
                    SortTypeUiModel.PointDesc -> true
                    SortTypeUiModel.PointAsc -> false
                    else -> null
                }
            )
        }
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val completedQuests = myInfo.flatMapLatest { myInfo ->
        val areaCode = myInfo.myCommericalAreaCode
        val isZoneCode = myInfo.isCommercialAreaCode
        selectedSortType.flatMapLatest {
            questRepository.getTypedQuests(
                commercialAreaCode = areaCode,
                isZoneCode = isZoneCode,
                orderRewardDesc = when (it) {
                    SortTypeUiModel.PointDesc -> true
                    SortTypeUiModel.PointAsc -> false
                    else -> null
                },
                completedYn = true
            )
        }
    }.cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val typedQuests = selectedQuestTab.flatMapLatest { selectedQuestTab ->
        combine(
            normalQuests,
            repeatQuests,
            eventQuests,
            completedQuests
        ) { normalQuests, repeatQuests, eventQuests, completedQuests ->
            when (selectedQuestTab) {
                QuestTabUiModel.NORMAL -> normalQuests
                QuestTabUiModel.REPEAT -> repeatQuests
                QuestTabUiModel.EVENT -> eventQuests
            }
        }.combine(favoriteQuestSet) { typedQuests, favoriteQuestSet ->
            typedQuests.map {
                if (it.questId in favoriteQuestSet) it.copy(favoriteYn = true) else it
            }
        }.combine(unfavoriteQuests) { typedQuests, unfavoriteQuests ->
            typedQuests.map {
                if (it.questId in unfavoriteQuests) it.copy(favoriteYn = false) else it
            }
        }
    }.cachedIn(viewModelScope)

    fun selectQuest(questId: Int) {
        selectedQuestId.update { questId }
    }

    fun unselectQuest() {
        selectedQuestId.update { null }
    }

    fun selectQuestType(questTab: QuestTabUiModel) {
        _selectedQuestTab.update { questTab }
        if (questTab == QuestTabUiModel.REPEAT) {
            _selectedRepeatType.update { RepeatQuestTypeUiModel.Daily }
        }
    }

    fun selectRepeatPeriod(repeatType: RepeatQuestTypeUiModel) {
        _selectedRepeatType.update { repeatType }
    }

    fun selectSortType(sortType: SortTypeUiModel) {
        _selectedSortType.update { sortType }
    }

    fun updateQuestFavoriteStatus(questId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            if (!isFavorite) {
                questRepository.registerFavoriteQuest(questId).onSuccess {
                    questDetailRefreshTrigger.emit(Unit)
                    favoriteQuestSet.update { it + questId }
                    if (questId in unfavoriteQuests.value) {
                        unfavoriteQuests.update { it - questId }
                    }
                }
            } else {
                questRepository.deleteFavoriteQuest(questId).onSuccess {
                    questDetailRefreshTrigger.emit(Unit)
                    unfavoriteQuests.update { it + questId }
                    if (questId in unfavoriteQuests.value) {
                        favoriteQuestSet.update { it - questId }
                    }
                }
            }
        }
    }
}