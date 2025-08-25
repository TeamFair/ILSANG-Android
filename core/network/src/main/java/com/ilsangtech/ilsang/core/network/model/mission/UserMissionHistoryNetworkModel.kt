package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class UserMissionHistoryNetworkModel(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String,
    val createdAt: String
)