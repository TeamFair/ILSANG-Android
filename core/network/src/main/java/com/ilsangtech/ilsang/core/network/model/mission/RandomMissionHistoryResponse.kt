package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class RandomMissionHistoryResponse(
    val content: List<RandomMissionHistoryNetworkModel>,
    val isLast: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)