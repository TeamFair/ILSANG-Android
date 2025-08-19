package com.ilsangtech.ilsang.core.network.model.area

import kotlinx.serialization.Serializable

@Serializable
data class MetroAreaNetworkModel(
    val areaName: String,
    val code: String,
    val commercialAreaNetworkModel: List<CommercialAreaNetworkModel>
)