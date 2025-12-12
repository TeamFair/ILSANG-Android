package com.ilsangtech.ilsang.core.data.comment.datasource

import com.ilsangtech.ilsang.core.network.api.CommentApiService
import com.ilsangtech.ilsang.core.network.model.comment.CommentCreationRequest

class CommentDataSourceImpl(
    private val commentApiService: CommentApiService
) : CommentDataSource {
    override suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ) {
        return commentApiService.createComment(
            missionHistoryId = missionHistoryId,
            request = CommentCreationRequest(
                parentId = parentId,
                comment = comment
            )
        )
    }

    override suspend fun deleteComment(commentId: Int) {
        return commentApiService.deleteComment(commentId)
    }
}