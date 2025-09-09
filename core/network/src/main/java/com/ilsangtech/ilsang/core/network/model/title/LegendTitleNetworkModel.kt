package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class LegendTitleNetworkModel(
    val titleHistoryId: Int,
    val userId: String,
    val name: String,
    val grade: String,
    val type: String,
    val createdAt: String
)