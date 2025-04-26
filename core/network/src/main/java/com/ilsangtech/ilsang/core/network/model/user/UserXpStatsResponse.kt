package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserXpStatsResponse(
    @SerialName("data") val userXpStatsNetworkModel: UserXpStatsNetworkModel,
    val message: String,
    val status: String
)