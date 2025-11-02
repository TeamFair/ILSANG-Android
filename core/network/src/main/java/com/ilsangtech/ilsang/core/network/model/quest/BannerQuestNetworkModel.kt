package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class BannerQuestNetworkModel(
    val questId: Int,
    val questType: String,
    val repeatFrequency: String?,
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val rewards: List<RewardPointNetworkModel>,
    val title: String,
    val writerName: String,
    val commercialAreaCode: String,
    val lastCompleteDate: String?
)