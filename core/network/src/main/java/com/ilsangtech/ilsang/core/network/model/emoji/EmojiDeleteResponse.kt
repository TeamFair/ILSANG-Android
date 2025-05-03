package com.ilsangtech.ilsang.core.network.model.emoji

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class EmojiDeleteResponse(
    val `data`: JsonObject,
    val message: String,
    val status: String
)