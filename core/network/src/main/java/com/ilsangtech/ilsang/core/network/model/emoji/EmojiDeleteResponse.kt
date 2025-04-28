package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.Serializable

@Serializable
data class EmojiDeleteResponse(
    val `data`: String?,
    val message: String,
    val status: String
)