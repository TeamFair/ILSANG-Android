package com.ilsangtech.ilsang.core.data.comment.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toTitle
import com.ilsangtech.ilsang.core.model.comment.Comment
import com.ilsangtech.ilsang.core.model.comment.CommentWriter
import com.ilsangtech.ilsang.core.network.model.comment.CommentNetworkModel
import com.ilsangtech.ilsang.core.network.model.comment.CommentWriterNetworkModel

internal fun CommentNetworkModel.toComment(): Comment {
    return Comment(
        id = id,
        parentId = parentId,
        comment = comment,
        writer = writer.toCommentWriter(),
        createdAt = createdAt,
        hasReported = hasReportedYn,
        isDeleted = deleteYn,
        children = children.map(CommentNetworkModel::toComment)
    )
}

internal fun CommentWriterNetworkModel.toCommentWriter(): CommentWriter {
    return CommentWriter(
        userId = userId,
        nickname = nickname,
        profileImageId = profileImageId,
        title = title.toTitle()
    )
}