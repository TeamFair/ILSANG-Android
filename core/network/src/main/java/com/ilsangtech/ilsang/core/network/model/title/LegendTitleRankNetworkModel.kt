package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class LegendTitleRankNetworkModel(
    val userId: String,
    val profileImageId: String?,
    val nickName: String,
    val point: Int,
    val rank: Int,
    val title: LegendTitleNetworkModel
)