package com.ilsangtech.ilsang.core.network.model.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class LogoutResponse(
    val data: JsonObject,
    val message: String,
    val status: String
)