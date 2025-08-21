package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class RewardPointNetworkModel(
    val point: Int,
    val pointType: String
)