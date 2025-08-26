package com.ilsangtech.ilsang.core.network.model.quest

import com.ilsangtech.ilsang.core.network.model.mission.MissionNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class QuestDetailResponse(
    val expireDate: String?,
    val favoriteYn: Boolean,
    val id: Int,
    val imageId: String,
    val mainImageId: String,
    val missions: List<MissionNetworkModel>,
    val questType: String,
    val repeatFrequency: String?,
    val rewards: List<RewardPointNetworkModel>,
    val title: String,
    val userRank: Int?,
    val writerName: String
)