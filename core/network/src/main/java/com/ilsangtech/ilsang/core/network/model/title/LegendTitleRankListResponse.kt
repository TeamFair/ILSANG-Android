package com.ilsangtech.ilsang.core.network.model.title

import kotlinx.serialization.Serializable

@Serializable
data class LegendTitleRankListResponse(
    val content: List<LegendTitleRankNetworkModel>,
    val isLast: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)