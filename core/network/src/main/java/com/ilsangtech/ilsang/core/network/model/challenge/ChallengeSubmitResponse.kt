package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeSubmitResponse(
    @SerialName("data") val challengeSubmitData: ChallengeSubmitData,
    val message: String,
    val status: String
)