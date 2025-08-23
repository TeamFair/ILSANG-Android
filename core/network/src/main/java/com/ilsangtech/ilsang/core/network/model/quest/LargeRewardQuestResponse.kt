package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class LargeRewardQuestResponse(
    val content: List<LargeRewardQuestNetworkModel>,
    val isLast: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)