package com.ilsangtech.ilsang.feature.submit

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ilsangtech.ilsang.core.domain.MissionRepository
import com.ilsangtech.ilsang.core.domain.QuestCompleteDateRepository
import com.ilsangtech.ilsang.core.domain.QuestRepository
import com.ilsangtech.ilsang.core.domain.QuizRepository
import com.ilsangtech.ilsang.feature.submit.model.SubmitQuestUiState
import com.ilsangtech.ilsang.feature.submit.model.SubmitResultUiState
import com.ilsangtech.ilsang.feature.submit.model.WordsQuizUiState
import com.ilsangtech.ilsang.feature.submit.navigation.WordsQuizSubmitRoute
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
class WordsQuizSubmitViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    questRepository: QuestRepository,
    quizRepository: QuizRepository,
    private val missionRepository: MissionRepository,
    private val questCompleteDateRepository: QuestCompleteDateRepository
) : ViewModel() {
    private val questId = savedStateHandle.toRoute<WordsQuizSubmitRoute>().questId
    private val missionId = savedStateHandle.toRoute<WordsQuizSubmitRoute>().missionId
    val isIsZoneQuest = savedStateHandle.toRoute<WordsQuizSubmitRoute>().isIsZoneQuest

    private val _submitResultUiState =
        MutableStateFlow<SubmitResultUiState>(SubmitResultUiState.NotSubmitted)
    val submitResultUiState = _submitResultUiState.asStateFlow()

    val submitAnswerState = TextFieldState()

    val wordsQuizUiState = combine(
        questRepository.getQuestDetail(questId),
        quizRepository.getRandomQuiz(missionId)
    ) { quest, quiz ->
        WordsQuizUiState.Success(
            quizId = quiz.id,
            question = quiz.question,
            hint = quiz.hint.orEmpty(),
            submitQuestUiState = SubmitQuestUiState(
                questImageId = quest.imageId,
                title = quest.title,
                writerName = quest.writerName,
                questType = quest.questType,
                rewards = quest.rewards
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WordsQuizUiState.Loading
    )

    fun resetResultUiState() {
        _submitResultUiState.update { SubmitResultUiState.NotSubmitted }
    }

    fun submitMission() {
        viewModelScope.launch {
            val wordsQuizUiState = wordsQuizUiState.value as? WordsQuizUiState.Success
            wordsQuizUiState?.let { wordsQuizUiState ->
                missionRepository.submitQuizMission(
                    missionId = missionId,
                    quizId = wordsQuizUiState.quizId,
                    answer = submitAnswerState.text.toString()
                ).onSuccess { isCorrect ->
                    if (isCorrect) {
                        _submitResultUiState.update {
                            val rewardPoints = wordsQuizUiState.submitQuestUiState.rewards
                            SubmitResultUiState.Success(rewardPoints)
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