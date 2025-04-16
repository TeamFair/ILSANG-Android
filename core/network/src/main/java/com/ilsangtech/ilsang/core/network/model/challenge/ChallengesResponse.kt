package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengesResponse(
    @SerialName("data") val challengeList: List<ChallengeNetworkModel>,
    val message: String,
    val page: Int,
    val size: Int,
    val status: String,
    val total: Int
)