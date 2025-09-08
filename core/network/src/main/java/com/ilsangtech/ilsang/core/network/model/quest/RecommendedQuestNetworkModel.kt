package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class RecommendedQuestNetworkModel(
    val imageId: String?,
    val mainImageId: String?,
    val questId: Int,
    val title: String,
    val writerName: String
)