package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.Serializable

@Serializable
data class TypedQuestNetworkModel(
    val questId: Int,
    val expireDate: String,
    val favoriteYn: Boolean,
    val imageId: String,
    val mainImageId: String?,
    val rewards: List<RewardPointNetworkModel>,
    val title: String,
    val writerName: String,
    val questType: String,
    val repeatFrequency: String?,
    val lastCompleteDate: String?
)