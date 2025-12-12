package com.ilsangtech.ilsang.core.domain

interface CommentRepository {
    suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ): Result<Unit>
}