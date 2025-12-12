package com.ilsangtech.ilsang.core.data.comment.datasource

import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel

interface CommentDataSource {
    suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    )

    suspend fun deleteComment(commentId: Int)

    suspend fun getComments(missionHistoryId: Int): List<CommentNetworkModel>
}