package com.ilsangtech.ilsang.feature.quest

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.model.Quest
import com.ilsangtech.ilsang.core.model.QuestType
import com.ilsangtech.ilsang.core.model.RepeatQuestPeriod
import com.ilsangtech.ilsang.core.model.RewardType
import com.ilsangtech.ilsang.core.util.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestTabViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val _selectedQuestId = MutableStateFlow<String?>(null)
    private val selectedQuestId = _selectedQuestId.asStateFlow()

    private var _selectedQuestType = MutableStateFlow(QuestType.NORMAL)
    val selectedQuestType = _selectedQuestType.asStateFlow()

    private var _selectedRewardType = MutableStateFlow(RewardType.STRENGTH)
    val selectedRewardType = _selectedRewardType.asStateFlow()

    private var _selectedRepeatPeriod = MutableStateFlow<RepeatQuestPeriod>(RepeatQuestPeriod.DAILY)
    val selectedRepeatPeriod = _selectedRepeatPeriod.asStateFlow()

    private var _selectedSortType = MutableStateFlow(SortType.POINT_DESC)
    val selectedSortType = _selectedSortType.asStateFlow()

    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val questTabUiState: StateFlow<QuestTabUiState> = combine(
        selectedQuestType, selectedRepeatPeriod
    ) { type, period ->
        type to period
    }.flatMapLatest { (questType, repeatPeriod) ->
        when (questType) {
            QuestType.NORMAL -> questRepository.getUncompletedNormalQuests()
            QuestType.REPEAT -> questRepository.getUncompletedRepeatQuests(
                when (repeatPeriod) {
                    RepeatQuestPeriod.DAILY -> "DAILY"
                    RepeatQuestPeriod.WEEKLY -> "WEEKLY"
                    RepeatQuestPeriod.MONTHLY -> "MONTHLY"
                }
            )

            QuestType.EVENT -> questRepository.getUncompletedEventQuests()
            else -> emptyFlow()
        }
    }.combine(selectedRewardType) { quests, rewardType ->
        quests.filter { quest ->
            quest.rewardList.any { it.content == rewardType.name }
        }
    }
    }.combine<List<Quest>, SortType, QuestTabUiState>(selectedSortType) { quests, sortType ->
        val sortedQuests = when (sortType) {
            SortType.POINT_ASC -> {
                quests.sortedBy { quest ->
                    quest.rewardList.sumOf { it.quantity }
                }
            }

            SortType.POINT_DESC -> {
                quests.sortedByDescending { quest ->
                    quest.rewardList.sumOf { it.quantity }
                }
            }

            SortType.FAVORITE -> {
                quests.filter { it.favoriteYn }
            }

            SortType.POPULAR -> {
                quests.sortedByDescending { it.score }
            }
        }
        QuestTabUiState.Success(QuestTabUiData(sortedQuests))
    }
        .catch { emit(QuestTabUiState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuestTabUiState.Loading
        )

    val selectedQuest = selectedQuestId.combine(questTabUiState) { questId, uiState ->
        if (questId == null || uiState !is QuestTabUiState.Success) return@combine null
        uiState.data.questList.find { it.questId == questId }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun selectQuest(quest: Quest) {
        _selectedQuestId.update { quest.questId }
    }

    fun unselectQuest() {
        _selectedQuestId.update { null }
    }

    fun selectQuestType(questType: QuestType) {
        _selectedQuestType.value = questType
    }

    fun selectRewardType(rewardType: RewardType) {
        _selectedRewardType.value = rewardType
    }

    fun selectRepeatPeriod(repeatQuestPeriod: RepeatQuestPeriod) {
        _selectedRepeatPeriod.value = repeatQuestPeriod
    }

    fun selectSortType(sortType: SortType) {
        _selectedSortType.update { sortType }
    }

    fun setCapturedImageUri(uri: Uri) {
        _capturedImageUri.value = uri
    }

    fun updateQuestFavoriteStatus(quest: Quest) {
        viewModelScope.launch {
            if (quest.favoriteYn) {
                questRepository.deleteFavoriteQuest(quest.questId)
            } else {
                questRepository.registerFavoriteQuest(quest.questId)
            }
        }
    }
}