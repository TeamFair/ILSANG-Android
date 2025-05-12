package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.Serializable

@Serializable
data class EmojiRegistrationData(
    val emojiId: String,
    val emojiStatus: String,
    val targetId: String,
    val targetType: String
)