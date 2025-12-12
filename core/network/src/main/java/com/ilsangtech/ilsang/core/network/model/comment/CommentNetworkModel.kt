package com.ilsangtech.ilsang.core.network.model.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentNetworkModel(
    val id: Int,
    val parentId: Int?,
    val comment: String,
    val writer: CommentWriterNetworkModel,
    val status: String,
    val createdAt: String,
    val hasReportedYn: Boolean,
    val deleteYn: Boolean
)
