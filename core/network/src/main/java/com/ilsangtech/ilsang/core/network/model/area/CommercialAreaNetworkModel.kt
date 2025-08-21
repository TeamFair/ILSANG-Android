package com.ilsangtech.ilsang.core.network.model.area

import kotlinx.serialization.Serializable

@Serializable
data class CommercialAreaNetworkModel(
    val areaName: String,
    val code: String,
    val createdAt: String,
    val createdBy: String,
    val description: String,
    val images: List<String>,
    val metroAreaCode: String,
    val updatedAt: String,
    val updatedBy: String
)