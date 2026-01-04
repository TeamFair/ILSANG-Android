package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class RandomMissionHistoryNetworkModel(
    val missionHistoryId: Int,
    val questId: Int,
    val commercialAreaCode: String,
    val createdAt: String,
    val currentUserEmojis: List<String>,
    val hateCount: Int,
    val likeCount: Int,
    val submitImageId: String,
    val title: String,
    val writerName: String,
    val user: MissionHistoryUserNetworkModel,
    val viewCount: Int,
    val questType: String,
    val repeatFrequency: String?,
    val expireDate: String?,
    val lastCompleteDate: String?,
    val shareCount: Int,
    val commentCount: Int
)