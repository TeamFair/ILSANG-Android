package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class MetroAreaRankNetworkModel(
    val areaName: String,
    val images: List<String>,
    val metroCode: String,
    val point: Int
)