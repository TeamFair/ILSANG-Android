package com.ilsangtech.ilsang.feature.submit

import androidx.compose.foundation.text.input.TextFieldState

sealed interface QuizUiState {
    data class OxQuizUiState(
        val question: String,
        val submitState: OxQuizSubmitUiState
    ) : QuizUiState

    data class WordsQuizUiState(
        val question: String,
        val hint: String,
        val answer: TextFieldState
    ) : QuizUiState
}

sealed interface OxQuizSubmitUiState {
    data object NotSelected : OxQuizSubmitUiState
    data object Correct : OxQuizSubmitUiState
    data object Incorrect : OxQuizSubmitUiState
}