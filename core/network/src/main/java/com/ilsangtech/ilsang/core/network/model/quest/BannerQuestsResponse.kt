package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class BannerQuestsResponse(
    val content: List<BannerQuestNetworkModel>,
    val isLast: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)