package com.ilsangtech.ilsang.core.model.mission

import com.ilsangtech.ilsang.core.model.quest.QuestType

data class RandomMissionHistory(
    val commercialAreaCode: String,
    val createdAt: String,
    val currentUserEmojis: List<String>,
    val hateCount: Int,
    val likeCount: Int,
    val missionHistoryId: Int,
    val submitImageId: String,
    val title: String,
    val writerName: String,
    val user: MissionHistoryUser,
    val viewCount: Int,
    val questType: QuestType,
    val shareCount: Int,
    val commentCount: Int,
    val lastCompleteDate: String?,
    val expireDate: String?
)
