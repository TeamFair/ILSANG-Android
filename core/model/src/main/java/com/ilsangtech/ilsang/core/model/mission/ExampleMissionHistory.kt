package com.ilsangtech.ilsang.core.model.mission

data class ExampleMissionHistory(
    val commercialAreaCode: String,
    val createdAt: String,
    val currentUserEmojis: List<String>,
    val hateCount: Int,
    val likeCount: Int,
    val missionHistoryId: Int,
    val submitImageId: String,
    val title: String,
    val user: MissionHistoryUser,
    val viewCount: Int
)