package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class PopularQuestNetworkModel(
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val questId: Int,
    val questType: String,
    val repeatFrequency: String?,
    val title: String,
    val writerName: String
)