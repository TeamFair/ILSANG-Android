package com.ilsangtech.ilsang.core.network.model.area

import kotlinx.serialization.Serializable

@Serializable
data class MetroAreaNetworkModel(
    val areaName: String,
    val code: String,
    val commercialAreas: List<CommercialAreaNetworkModel>,
    val createdAt: String,
    val createdBy: String,
    val images: List<String>,
    val updatedAt: String,
    val updatedBy: String
)