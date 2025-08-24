package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserCommercialPointResponse(
    val topCommercialArea: TopCommercialAreaNetworkModel,
    val totalOwnerContributions: List<TotalOwnerContributionNetworkModel>
)