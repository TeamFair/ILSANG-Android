package com.ilsangtech.ilsang.feature.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ilsangtech.ilsang.core.domain.AreaRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.UserRepository
import com.ilsangtech.ilsang.core.model.NewQuestType
import com.ilsangtech.ilsang.feature.quest.model.QuestFilterCondition
import com.ilsangtech.ilsang.feature.quest.model.QuestTabUiModel
import com.ilsangtech.ilsang.feature.quest.model.RepeatQuestTypeUiModel
import com.ilsangtech.ilsang.feature.quest.model.SortTypeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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
    private val _questDetailRefreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    val questDetailRefreshTrigger = _questDetailRefreshTrigger.asSharedFlow()

    private val _selectedQuestTab = MutableStateFlow(QuestTabUiModel.NORMAL)
    val selectedQuestTab = _selectedQuestTab.asStateFlow()

    private val _selectedRepeatType = MutableStateFlow<RepeatQuestTypeUiModel?>(null)
    val selectedRepeatType = _selectedRepeatType.asStateFlow()

    private val _selectedSortType = MutableStateFlow(SortTypeUiModel.PointDesc)
    val selectedSortType = _selectedSortType.asStateFlow()

    private val selectedQuestId = MutableStateFlow<Int?>(null)

    private val myInfo = userRepository.getMyInfo()

    val areaName = myInfo.map { myInfo ->
        areaRepository.getCommercialArea(
            commercialAreaCode = myInfo.myCommericalAreaCode
        ).areaName
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedQuest = combine(
        selectedQuestId,
        _questDetailRefreshTrigger.onStart { emit(Unit) }
    ) { questId, _ -> questId }.flatMapLatest { questId ->
        questId?.let { questRepository.getQuestDetail(questId) } ?: flowOf(null)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val typedQuests = combine(
        myInfo,
        selectedQuestTab,
        selectedRepeatType,
        selectedSortType
    ) { myInfo, questTab, repeatType, sortType ->
        val areaCode = myInfo.myCommericalAreaCode
        val questType = when (questTab) {
            QuestTabUiModel.NORMAL -> NewQuestType.Normal
            QuestTabUiModel.REPEAT -> when (repeatType) {
                RepeatQuestTypeUiModel.Daily -> NewQuestType.Repeat.Daily
                RepeatQuestTypeUiModel.Weekly -> NewQuestType.Repeat.Weekly
                RepeatQuestTypeUiModel.Monthly -> NewQuestType.Repeat.Monthly
                else -> null
            }

            QuestTabUiModel.EVENT -> NewQuestType.Event
            else -> null
        }
        val orderRewardDesc = when (sortType) {
            SortTypeUiModel.PointDesc -> true
            SortTypeUiModel.PointAsc -> false
            else -> null
        }
        val orderExpiredDesc = if (sortType == SortTypeUiModel.ExpireDate) true else null
        val completeYn = questTab == QuestTabUiModel.COMPLETED

        QuestFilterCondition(
            areaCode = areaCode,
            questType = questType,
            orderExpiredDesc = orderExpiredDesc,
            orderRewardDesc = orderRewardDesc,
            completeYn = completeYn,
        )
    }.flatMapLatest { questFilterCondition ->
        questRepository.getTypedQuests(
            commercialAreaCode = questFilterCondition.areaCode,
            questType = questFilterCondition.questType,
            orderExpiredDesc = questFilterCondition.orderExpiredDesc,
            orderRewardDesc = questFilterCondition.orderRewardDesc,
            completeYn = questFilterCondition.completeYn
        )
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
            val result = if (isFavorite) {
                questRepository.deleteFavoriteQuest(questId)
            } else {
                questRepository.registerFavoriteQuest(questId)
            }

            result.onSuccess {
                _questDetailRefreshTrigger.emit(Unit)
            }
        }
    }
}