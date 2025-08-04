package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class FavoriteQuestDeletionResponse(
    val data: JsonObject,
    val status: String,
    val message: String
)