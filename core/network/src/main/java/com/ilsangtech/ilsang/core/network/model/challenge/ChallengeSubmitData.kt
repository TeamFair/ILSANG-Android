package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeSubmitData(
    val challengeId: String
)