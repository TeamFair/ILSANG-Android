package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class UserMissionHistoryNetworkModel(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String?,
    val questImageId: String?,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String,
    val commercialAreaCode: String,
    val missionType: String
)