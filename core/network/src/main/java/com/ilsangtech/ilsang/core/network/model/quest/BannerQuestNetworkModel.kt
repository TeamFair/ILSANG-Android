package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class BannerQuestNetworkModel(
    val expireDate: String,
    val imageId: String?,
    val mainImageId: String?,
    val questId: Int,
    val rewards: List<RewardPointNetworkModel>,
    val title: String,
    val writerName: String
)