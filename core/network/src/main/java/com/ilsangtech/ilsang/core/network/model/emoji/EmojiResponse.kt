package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiResponse(
    @SerialName("data") val emojiData: EmojiData,
    val message: String,
    val status: String
)