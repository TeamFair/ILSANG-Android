package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.Serializable

@Serializable
data class EmojiData(
    val likeId: String?,
    val isLike: Boolean,
    val hateId: String?,
    val isHate: Boolean
)