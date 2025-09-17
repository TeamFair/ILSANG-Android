package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class SeasonRewardTitleNetworkModel(
    val id: String,
    val name: String,
    val type: String,
    val grade: String,
    val condition: String,
    val useYn: Boolean
)