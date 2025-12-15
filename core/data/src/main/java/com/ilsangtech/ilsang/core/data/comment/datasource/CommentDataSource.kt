package com.ilsangtech.ilsang.core.data.comment.datasource

import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel
import com.ilsangtech.ilsang.core.network.model.comment.CommentReportResponse

interface CommentDataSource {
    suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    )

    suspend fun deleteComment(commentId: Int)

    suspend fun getComments(missionHistoryId: Int): List<CommentNetworkModel>

    suspend fun reportComment(
        commentId: Int,
        reason: String
    ): CommentReportResponse
}