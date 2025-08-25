package com.ilsangtech.ilsang.core.model.rank

data class MetroAreaRank(
    val metroAreaCode: String,
    val areaName: String,
    val point: Int,
    val images: List<String>,
    val rank: Int
)
