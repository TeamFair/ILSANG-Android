package com.ilsangtech.ilsang.feature.approval.model

sealed interface ReportResultUiState {
    data object UnReported : ReportResultUiState
    data object Success : ReportResultUiState
    data object Failure : ReportResultUiState
    data object Reported : ReportResultUiState
}