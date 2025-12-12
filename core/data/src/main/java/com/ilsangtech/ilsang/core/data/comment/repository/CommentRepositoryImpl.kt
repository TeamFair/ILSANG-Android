package com.ilsangtech.ilsang.core.data.comment.repository

import com.ilsangtech.ilsang.core.data.comment.datasource.CommentDataSource
import com.ilsangtech.ilsang.core.data.comment.mapper.toComment
import com.ilsangtech.ilsang.core.domain.CommentRepository
import com.ilsangtech.ilsang.core.model.comment.Comment
import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel

class CommentRepositoryImpl(
    private val commentDataSource: CommentDataSource
) : CommentRepository {
    override suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ): Result<Unit> {
        return runCatching {
            commentDataSource.createComment(
                missionHistoryId = missionHistoryId,
                parentId = parentId,
                comment = comment
            )
        }
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> {
        return runCatching { commentDataSource.deleteComment(commentId) }
    }

    override suspend fun getComments(missionHistoryId: Int): List<Comment> {
        return commentDataSource.getComments(missionHistoryId)
            .map(CommentNetworkModel::toComment)
    }
}