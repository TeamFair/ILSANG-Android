package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class TopCommercialAreaNetworkModel(
    val commercialAreaCode: String,
    val ownerContributionPercent: Double,
    val point: Int
)