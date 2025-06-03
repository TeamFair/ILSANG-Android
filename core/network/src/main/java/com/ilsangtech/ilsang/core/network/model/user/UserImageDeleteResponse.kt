package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class UserImageDeleteResponse(
    val data: JsonObject,
    val status: String,
    val message: String
)