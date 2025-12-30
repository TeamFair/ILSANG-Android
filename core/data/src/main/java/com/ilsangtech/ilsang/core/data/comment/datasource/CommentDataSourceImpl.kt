package com.ilsangtech.ilsang.core.data.comment.datasource

import com.ilsangtech.ilsang.core.network.api.CommentApiService
import com.ilsangtech.ilsang.core.network.model.comment.CommentCreationRequest
import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel
import com.ilsangtech.ilsang.core.network.model.comment.CommentReportRequest
import com.ilsangtech.ilsang.core.network.model.comment.CommentReportResponse
import com.ilsangtech.ilsang.core.network.model.common.ErrorResponse
import kotlinx.serialization.json.Json

class CommentDataSourceImpl(
    private val commentApiService: CommentApiService
) : CommentDataSource {
    override suspend fun createComment(
        missionHistoryId: Int,
        parentId: Int?,
        comment: String
    ): Result<String?> {
        val response = commentApiService.createComment(
            missionHistoryId = missionHistoryId,
            request = CommentCreationRequest(
                parentId = parentId,
                comment = comment
            )
        )
        return if (response.isSuccessful) {
            Result.success(null)
        } else {
            val errorBody =
                response.errorBody()?.string() ?: return Result.failure(Throwable("Unknown error"))
            val errorMessage = Json.decodeFromString<ErrorResponse>(errorBody)
            errorMessage.message?.let { message ->
                Result.success(message)
            } ?: Result.failure(Throwable("Unknown error"))
        }
    }

    override suspend fun deleteComment(commentId: Int) {
        return commentApiService.deleteComment(commentId)
    }

    override suspend fun getComments(missionHistoryId: Int): List<CommentNetworkModel> {
        return commentApiService.getComments(missionHistoryId)
    }

    override suspend fun reportComment(
        commentId: Int,
        reason: String
    ): CommentReportResponse {
        return commentApiService.reportComment(
            commentId = commentId,
            request = CommentReportRequest(reason)
        )
    }
}