package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomChallengeResponse(
    @SerialName("data") val randomChallengeData: List<RandomChallengeNetworkModel>,
    val message: String,
    val page: Int,
    val size: Int,
    val status: String,
    val total: Int
)