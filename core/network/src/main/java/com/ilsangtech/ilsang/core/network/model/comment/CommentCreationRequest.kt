package com.ilsangtech.ilsang.core.network.model.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentCreationRequest(
    val parentId: Int?,
    val comment: String
)
