package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class UserImageUpdateResponse(
    val data: JsonObject,
    val status: Boolean,
    val message: String
)
