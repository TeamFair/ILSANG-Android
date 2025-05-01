package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiRegistrationResponse(
    @SerialName("data") val emojiRegistrationData: EmojiRegistrationData,
    val message: String,
    val status: String
)