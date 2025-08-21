package com.ilsangtech.ilsang.core.network.model.season

import kotlinx.serialization.Serializable

@Serializable
data class SeasonNetworkModel(
    val endDate: String,
    val id: Int,
    val seasonNumber: Int,
    val startDate: String
)