package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class UncompletedEventQuestResponse(
    val `data`: List<QuestNetworkModel>,
    val message: String,
    val page: Int,
    val size: Int,
    val status: String,
    val total: Int
)
