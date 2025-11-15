package com.ilsangtech.ilsang.feature.submit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.QuizRepository
import com.ilsangtech.ilsang.feature.submit.model.OxQuizSubmitUiState
import com.ilsangtech.ilsang.feature.submit.model.OxQuizUiState
import com.ilsangtech.ilsang.feature.submit.model.SubmitQuestUiState
import com.ilsangtech.ilsang.feature.submit.model.SubmitResultUiState
import com.ilsangtech.ilsang.feature.submit.navigation.OxQuizSubmitRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OxQuizSubmitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    quizRepository: QuizRepository,
    private val questRepository: QuestRepository,
    private val missionRepository: MissionRepository,
    private val questCompleteDateRepository: QuestCompleteDateRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<OxQuizSubmitRoute>().questId
    private val missionId = savedStateHandle.toRoute<OxQuizSubmitRoute>().missionId
    val isIsZoneQuest = savedStateHandle.toRoute<OxQuizSubmitRoute>().isIsZoneQuest

    private val _quizSubmitUiState =
        MutableStateFlow<OxQuizSubmitUiState>(OxQuizSubmitUiState.NotSelected)
    val quizSubmitUiState = _quizSubmitUiState.asStateFlow()

    private val _submitResultUiState =
        MutableStateFlow<SubmitResultUiState>(SubmitResultUiState.NotSubmitted)
    val submitResultUiState = _submitResultUiState.asStateFlow()

    val oxQuizUiState = combine(
        quizRepository.getRandomQuiz(missionId),
        quizSubmitUiState,
        questRepository.getQuestDetail(questId)
    ) { quiz, submitState, quest ->
        val submitQuestUiState = SubmitQuestUiState(
            questImageId = quest.imageId,
            title = quest.title,
            writerName = quest.writerName,
            questType = quest.questType,
            rewards = quest.rewards
        )

        OxQuizUiState.Success(
            quizId = quiz.id,
            question = quiz.question,
            submitQuestUiState = submitQuestUiState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = OxQuizUiState.Loading
    )

    fun updateSubmitState(state: OxQuizSubmitUiState) {
        _quizSubmitUiState.update { submitUiState ->
            when (submitUiState) {
                OxQuizSubmitUiState.NotSelected -> state
                OxQuizSubmitUiState.Correct -> {
                    if (state == OxQuizSubmitUiState.Correct) {
                        OxQuizSubmitUiState.NotSelected
                    } else {
                        state
                    }
                }

                OxQuizSubmitUiState.Incorrect -> {
                    if (state == OxQuizSubmitUiState.Incorrect) {
                        OxQuizSubmitUiState.NotSelected
                    } else {
                        state
                    }
                }
            }
        }
    }

    fun resetResultUiState() {
        _submitResultUiState.update { SubmitResultUiState.NotSubmitted }
    }

    fun submitMission() {
        viewModelScope.launch {
            _submitResultUiState.update { SubmitResultUiState.Loading }
            (oxQuizUiState.value as? OxQuizUiState.Success)?.let { (quizId, _, submitQuestUiState) ->
                missionRepository.submitQuizMission(
                    missionId = missionId,
                    quizId = quizId,
                    answer = when (_quizSubmitUiState.value) {
                        OxQuizSubmitUiState.Correct -> "O"
                        OxQuizSubmitUiState.Incorrect -> "X"
                        else -> throw IllegalStateException("Not Selected")
                    }
                ).onSuccess { isCorrect ->
                    if (isCorrect) {
                        _submitResultUiState.update {
                            SubmitResultUiState.Success(submitQuestUiState.rewards)
                        }
                        questCompleteDateRepository.updateQuestCompleteDate(questId)
                    } else {
                        _submitResultUiState.update { SubmitResultUiState.WrongAnswer }
                    }
                }.onFailure {
                    _submitResultUiState.update { SubmitResultUiState.Error }
                }
            }
        }
    }
}