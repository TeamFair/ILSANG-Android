package com.ilsangtech.ilsang.feature.home.submit

sealed interface SubmitUiState {
    data object NotSubmitted : SubmitUiState
    data object Loading : SubmitUiState
    data object Success : SubmitUiState
    data object Error : SubmitUiState
}