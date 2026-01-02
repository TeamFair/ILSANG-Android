package com.ilsangtech.ilsang.feature.approval.model

sealed interface ReportResultUiState {
    data object UnReported : ReportResultUiState
    data object Success : ReportResultUiState
    sealed interface Failure : ReportResultUiState {
        data object NetworkError : Failure
        data object AlreadyReported : Failure
    }
}