package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class RewardNetworkModel(
    val content: String?,
    val discountRate: Int?,
    val quantity: Int,
    val questId: String,
    val rewardId: String,
    val target: String?,
    val title: String,
    val type: String
)