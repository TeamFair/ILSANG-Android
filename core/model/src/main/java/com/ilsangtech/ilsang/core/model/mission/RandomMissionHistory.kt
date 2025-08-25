package com.ilsangtech.ilsang.core.model.mission

data class RandomMissionHistory(
    val commercialAreaCode: String,
    val createdAt: String,
    val currentUserEmojis: List<String>,
    val hateCount: Int,
    val likeCount: Int,
    val missionHistoryId: Int,
    val submitImageId: String,
    val title: String,
    val user: RandomMissionHistoryUser,
    val viewCount: Int
)
