package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.Serializable

@Serializable
data class EmojiRegistrationRequest(
    val emojiType: String,
    val targetId: String,
    val targetType: String
)