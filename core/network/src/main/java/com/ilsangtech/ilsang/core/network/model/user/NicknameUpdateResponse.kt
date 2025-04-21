package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class NicknameUpdateResponse(
    val data: JsonElement?,
    val status: String,
    val message: String
)