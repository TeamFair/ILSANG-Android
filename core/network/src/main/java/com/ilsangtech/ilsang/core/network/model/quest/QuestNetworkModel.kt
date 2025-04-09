package com.ilsangtech.ilsang.core.network.model.quest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestNetworkModel(
    val createDate: String,
    val creatorRole: String,
    val expireDate: String,
    val favoriteYn: Boolean,
    val imageId: String?,
    val mainImageId: String?,
    val marketId: String?,
    val missionId: String,
    val missionTitle: String,
    val missionType: String,
    val popularYn: Boolean,
    val questId: String,
    @SerialName("rewardList") val rewardNetworkModelList: List<RewardNetworkModel>,
    val score: Int,
    val status: String,
    val target: String,
    val type: String,
    val writer: String
)