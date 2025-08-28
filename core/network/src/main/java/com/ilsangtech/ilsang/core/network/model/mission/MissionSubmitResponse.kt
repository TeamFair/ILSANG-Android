package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class MissionSubmitResponse(
    val resultCode: String,
    val challengeId: Int?
)
