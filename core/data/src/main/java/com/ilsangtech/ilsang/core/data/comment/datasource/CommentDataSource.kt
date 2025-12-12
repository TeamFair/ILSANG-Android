package com.ilsangtech.ilsang.core.data.comment.datasource

interface CommentDataSource {
    suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    )
}