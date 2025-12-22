package com.ilsangtech.ilsang.core.model.comment

data class Comment(
    val id: Int,
    val parentId: Int?,
    val comment: String,
    val writer: CommentWriter,
    val createdAt: String,
    val hasReported: Boolean,
    val isDeleted: Boolean,
    val children: List<Comment>
)
