package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeSubmitRequest(
    val questId: String,
    val receiptImageId: String
)
