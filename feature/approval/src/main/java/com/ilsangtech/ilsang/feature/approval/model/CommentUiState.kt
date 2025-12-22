package com.ilsangtech.ilsang.feature.approval.model

sealed interface CommentUiState {
    data object Loading : CommentUiState
    data class Success(
        val comments: List<CommentUiModel>,
        val validCommentsSize: Int
    ) : CommentUiState

    data object Error : CommentUiState
}