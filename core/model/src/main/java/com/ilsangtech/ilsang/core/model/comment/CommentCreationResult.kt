package com.ilsangtech.ilsang.core.model.comment

sealed interface CommentCreationResult {
    data object Success : CommentCreationResult
    sealed interface Failure : CommentCreationResult {
        data object SpamPrevention : Failure
        data object UnknownError : Failure
    }
}