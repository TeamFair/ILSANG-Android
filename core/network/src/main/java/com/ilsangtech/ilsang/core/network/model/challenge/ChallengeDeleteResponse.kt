package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class ChallengeDeleteResponse(
    val data: JsonObject,
    val status: String,
    val message: String
)