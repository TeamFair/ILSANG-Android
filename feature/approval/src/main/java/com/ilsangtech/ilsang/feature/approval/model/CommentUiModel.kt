package com.ilsangtech.ilsang.feature.approval.model

import com.ilsangtech.ilsang.core.model.comment.CommentWriter

data class CommentUiModel(
    val id: Int,
    val parentId: Int?,
    val comment: String,
    val commentWriter: CommentWriter,
    val createdAt: String,
    val isMyComment: Boolean,
    val isReported: Boolean,
    val isDeleted: Boolean
)
