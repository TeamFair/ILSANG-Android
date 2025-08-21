package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class CommercialAreaRankNetworkModel(
    val areaName: String,
    val commercialCode: String,
    val images: List<String>,
    val point: Int
)