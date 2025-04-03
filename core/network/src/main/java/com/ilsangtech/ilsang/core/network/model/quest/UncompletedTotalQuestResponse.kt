package com.ilsangtech.ilsang.core.network.model.quest

data class UncompletedTotalQuestResponse(
    val `data`: List<QuestNetworkModel>,
    val message: String,
    val page: Int,
    val size: Int,
    val status: String,
    val total: Int
)