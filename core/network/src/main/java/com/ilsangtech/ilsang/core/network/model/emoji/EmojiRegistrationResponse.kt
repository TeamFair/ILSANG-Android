package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.Serializable

@Serializable
data class EmojiRegistrationResponse(
    val emojiRegistrationData: EmojiRegistrationData,
    val message: String,
    val status: String
)