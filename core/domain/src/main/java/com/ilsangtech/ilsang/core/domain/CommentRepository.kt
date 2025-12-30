package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.comment.Comment
import com.ilsangtech.ilsang.core.model.comment.CommentCreationResult

interface CommentRepository {
    suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ): CommentCreationResult

    suspend fun deleteComment(commentId: Int): Result<Unit>

    suspend fun getComments(missionHistoryId: Int): List<Comment>

    suspend fun reportComment(
        commentId: Int,
        reason: String
    ): Result<Boolean>
}