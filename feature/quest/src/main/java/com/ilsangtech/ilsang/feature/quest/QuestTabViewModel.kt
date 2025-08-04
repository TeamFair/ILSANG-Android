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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuestTabViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val _selectedQuest = MutableStateFlow<Quest?>(null)
    val selectedQuest = _selectedQuest.asStateFlow()

    private var _selectedQuestType = MutableStateFlow(QuestType.NORMAL)
    val selectedQuestType = _selectedQuestType.asStateFlow()

    private var _selectedRewardType = MutableStateFlow(RewardType.STRENGTH)
    val selectedRewardType = _selectedRewardType.asStateFlow()

    private var _selectedRepeatPeriod = MutableStateFlow<RepeatQuestPeriod>(RepeatQuestPeriod.DAILY)
    val selectedRepeatPeriod = _selectedRepeatPeriod.asStateFlow()

    private var _selectedSortType = MutableStateFlow("포인트 높은 순")
    val selectedSortType = _selectedSortType.asStateFlow()

    private val _capturedImageUri = MutableStateFlow<Uri?>(null)
    val capturedImageFile = MutableStateFlow(FileManager.createCacheFile(context)).asStateFlow()

    val questTabUiState: StateFlow<QuestTabUiState> = combine(
        selectedQuestType, selectedRepeatPeriod
    ) { questType, repeatPeriod ->
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
            else -> emptyList()
        }
    }
        .combine(selectedRewardType) { quests, rewardType ->
            quests.filter { quest ->
                quest.rewardList.find { it.content == rewardType.name } != null
            }
        }.combine<List<Quest>, String, QuestTabUiState>(selectedSortType) { quests, sortType ->
            val sortedQuests = quests.sortedBy { quest ->
                when (sortType) {
                    "포인트 높은 순" -> {
                        quest.rewardList.sumOf { reward ->
                            -reward.quantity
                        }
                    }

                    "포인트 낮은 순" -> {
                        quest.rewardList.sumOf { reward ->
                            reward.quantity
                        }
                    }

                    else -> {
                        -quest.score
                    }
                }
            }
            QuestTabUiState.Success(
                QuestTabUiData(
                    questList = sortedQuests
                )
            )
        }
        .catch {
            emit(QuestTabUiState.Error(it))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuestTabUiState.Loading
        )

    fun selectQuest(quest: Quest) {
        _selectedQuest.value = quest
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

    fun selectSortType(sortType: String) {
        _selectedSortType.value = sortType
    }

    fun setCapturedImageUri(uri: Uri) {
        _capturedImageUri.value = uri
    }
}