package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class MissionHistoryEmojiUpdateRequest(
    val emojiType: String
)