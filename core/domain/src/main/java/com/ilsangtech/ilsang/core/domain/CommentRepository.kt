package com.ilsangtech.ilsang.core.domain

import com.ilsangtech.ilsang.core.model.comment.Comment

interface CommentRepository {
    suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ): Result<Unit>

    suspend fun deleteComment(commentId: Int): Result<Unit>

    suspend fun getComments(missionHistoryId: Int): List<Comment>
}