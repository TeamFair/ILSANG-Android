package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPointSummaryResponse(
    val topCommercialAreaCode: String,
    val topContributionPoint: Int,
    val topMetroAreaCode: String
)