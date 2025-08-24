package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class TotalOwnerContributionNetworkModel(
    val commercialAreaCode: String,
    val point: Int
)