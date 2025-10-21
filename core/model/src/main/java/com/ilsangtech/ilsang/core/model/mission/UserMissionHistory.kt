package com.ilsangtech.ilsang.core.model.mission

data class UserMissionHistory(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String?,
    val questImageId: String?,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String,
    val missionType: MissionType,
    val commercialAreaCode: String
)
