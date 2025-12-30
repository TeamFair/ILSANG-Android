package com.ilsangtech.ilsang.core.data.comment.repository

import com.ilsangtech.ilsang.core.data.comment.datasource.CommentDataSource
import com.ilsangtech.ilsang.core.data.comment.mapper.toComment
import com.ilsangtech.ilsang.core.domain.CommentRepository
import com.ilsangtech.ilsang.core.model.comment.Comment
import com.ilsangtech.ilsang.core.model.comment.CommentCreationResult
import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel

class CommentRepositoryImpl(
    private val commentDataSource: CommentDataSource
) : CommentRepository {
    override suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ): CommentCreationResult {
        return commentDataSource.createComment(
            missionHistoryId = missionHistoryId,
            parentId = parentId,
            comment = comment
        ).fold(
            onSuccess = { message ->
                if (message == null) return@fold CommentCreationResult.Success
                if (message.contains("1 minute")) {
                    CommentCreationResult.Failure.SpamPrevention
                } else {
                    CommentCreationResult.Failure.UnknownError
                }
            },
            onFailure = {
                CommentCreationResult.Failure.UnknownError
            }
        )
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> {
        return runCatching { commentDataSource.deleteComment(commentId) }
    }

    override suspend fun getComments(missionHistoryId: Int): List<Comment> {
        return commentDataSource.getComments(missionHistoryId)
            .map(CommentNetworkModel::toComment)
    }

    override suspend fun reportComment(
        commentId: Int,
        reason: String
    ): Result<Boolean> {
        return runCatching {
            commentDataSource.reportComment(
                commentId = commentId,
                reason = reason
            ).resultCode == COMMENT_REPORT_SUCCESS_CODE
        }
    }

    private companion object {
        const val COMMENT_REPORT_SUCCESS_CODE = "S1000"
    }
}