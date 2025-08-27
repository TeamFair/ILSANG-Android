package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class ExampleMissionHistoryNetworkModel(
    val commercialAreaCode: String,
    val createdAt: String,
    val currentUserEmojis: List<String>,
    val hateCount: Int,
    val likeCount: Int,
    val missionHistoryId: Int,
    val submitImageId: String,
    val title: String,
    val user: MissionHistoryUserNetworkModel,
    val viewCount: Int
)