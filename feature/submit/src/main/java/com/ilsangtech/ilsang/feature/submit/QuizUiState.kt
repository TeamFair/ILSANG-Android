package com.ilsangtech.ilsang.feature.submit

sealed interface WordsQuizUiState {
    data object Loading : WordsQuizUiState
    data class Success(
        val quizId: Int?,
        val question: String,
        val hint: String,
        val submitQuestUiState: SubmitQuestUiState
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