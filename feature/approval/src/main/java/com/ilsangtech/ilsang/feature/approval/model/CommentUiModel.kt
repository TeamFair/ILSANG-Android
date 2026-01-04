package com.ilsangtech.ilsang.feature.approval.model

import com.ilsangtech.ilsang.core.model.comment.Comment
import com.ilsangtech.ilsang.core.model.comment.CommentWriter
import com.ilsangtech.ilsang.core.model.title.Title
import com.ilsangtech.ilsang.core.util.DateConverter

data class CommentUiModel(
    val id: Int,
    val parentId: Int?,
    val comment: String,
    val commentWriter: CommentWriterUiModel,
    val createdAt: String,
    val isMyComment: Boolean,
    val isReported: Boolean,
    val isDeleted: Boolean
) {
    data class CommentWriterUiModel(
        val userId: String,
        val nickname: String,
        val profileImageId: String?,
        val title: Title?,
        val isMissionHistoryUser: Boolean
    ) {
        val isDeletedUser get() = nickname.contains("삭제")
    }
}

internal fun Comment.toUiModel(
    isMyComment: Boolean,
    isMissionHistoryUser: Boolean
): CommentUiModel {
    return CommentUiModel(
        id = id,
        parentId = parentId,
        comment = comment,
        commentWriter = writer.toUiModel(isMissionHistoryUser),
        createdAt = DateConverter.formatDate(
            input = createdAt,
            outputPattern = "yyyy.MM.dd HH:mm"
        ),
        isMyComment = isMyComment,
        isReported = hasReported,
        isDeleted = isDeleted
    )
}

internal fun CommentWriter.toUiModel(
    isMissionHistoryUser: Boolean
): CommentUiModel.CommentWriterUiModel {
    return CommentUiModel.CommentWriterUiModel(
        userId = userId,
        nickname = nickname,
        profileImageId = profileImageId,
        title = title,
        isMissionHistoryUser = isMissionHistoryUser
    )
}