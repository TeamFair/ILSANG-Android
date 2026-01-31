package com.ilsangtech.ilsang.feature.quest_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.feature.quest_detail.model.QuestDetailUiState
import com.ilsangtech.ilsang.feature.quest_detail.model.toUiModel
import com.ilsangtech.ilsang.feature.quest_detail.navigation.QuestDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<QuestDetailRoute>().questId
    val questDetailUiState =
        questRepository.getQuestDetail(questId).map { questDetail ->
            QuestDetailUiState.Success(questDetail.toUiModel()) as QuestDetailUiState
        }.catch {
            emit(QuestDetailUiState.Error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuestDetailUiState.Loading
        )
}