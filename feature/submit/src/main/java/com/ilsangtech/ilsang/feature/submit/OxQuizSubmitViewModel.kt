package com.ilsangtech.ilsang.feature.submit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.QuizRepository
import com.ilsangtech.ilsang.feature.submit.navigation.OxQuizSubmitRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OxQuizSubmitViewModel(
    savedStateHandle: SavedStateHandle,
    quizRepository: QuizRepository,
    private val questRepository: QuestRepository,
    private val missionRepository: MissionRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<OxQuizSubmitRoute>().questId
    private val missionId = savedStateHandle.toRoute<OxQuizSubmitRoute>().missionId

    private val oxQuizSubmitUiState =
        MutableStateFlow<OxQuizSubmitUiState>(OxQuizSubmitUiState.NotSelected)

    private val _submitResultUiState = MutableStateFlow<SubmitUiState>(SubmitUiState.NotSubmitted)
    val submitResultUiState = _submitResultUiState.asStateFlow()

    val oxQuizUiState = combine(
        quizRepository.getRandomQuiz(missionId),
        oxQuizSubmitUiState
    ) { quiz, submitState ->
        QuizUiState.OxQuizUiState(
            quizId = quiz.id,
            question = quiz.question,
            submitState = submitState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = QuizUiState.OxQuizUiState(
            quizId = null,
            question = "",
            submitState = OxQuizSubmitUiState.NotSelected
        )
    )

    fun updateSubmitState(state: OxQuizSubmitUiState) {
        oxQuizSubmitUiState.update { state }
    }

    fun submitMission() {
        viewModelScope.launch {
            _submitResultUiState.update { SubmitUiState.Loading }
            oxQuizUiState.value.quizId?.let { quizId ->
                missionRepository.submitQuizMission(
                    missionId = missionId,
                    quizId = quizId,
                    answer = when (oxQuizUiState.value.submitState) {
                        OxQuizSubmitUiState.Correct -> "O"
                        OxQuizSubmitUiState.Incorrect -> "X"
                        else -> throw IllegalStateException("Not Selected")
                    }
                ).onSuccess {
                    questRepository.getQuestDetail(questId).collect { quest ->
                        _submitResultUiState.update {
                            SubmitUiState.Success(quest.rewards)
                        }
                    }
                }.onFailure {
                    _submitResultUiState.update { SubmitUiState.Error }
                }
            }
        }
    }
}