package com.ilsangtech.ilsang.feature.submit

import androidx.compose.foundation.text.input.TextFieldState

sealed interface WordsQuizUiState {
    data object Loading : WordsQuizUiState
    data class Success(
        val quizId: Int?,
        val question: String,
        val hint: String,
        val answer: TextFieldState
    ) : WordsQuizUiState
}

sealed interface OxQuizUiState {
    data object Loading : OxQuizUiState
    data class Success(
        val quizId: Int,
        val question: String,
        val submitQuestUiState: SubmitQuestUiState
    ) : OxQuizUiState
}

sealed interface OxQuizSubmitUiState {
    data object NotSelected : OxQuizSubmitUiState
    data object Correct : OxQuizSubmitUiState
    data object Incorrect : OxQuizSubmitUiState
}