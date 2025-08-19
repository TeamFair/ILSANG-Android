package com.ilsangtech.ilsang.core.network.model.area

import kotlinx.serialization.Serializable

@Serializable
data class CommercialAreaNetworkModel(
    val areaName: String,
    val code: String,
    val description: String,
    val metroAreaCode: String
)