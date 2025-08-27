package com.ilsangtech.ilsang.feature.approval.model

import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.util.DateConverter

data class RandomMissionHistoryUiModel(
    val commercialAreaName: String,
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

internal fun RandomMissionHistory.toUiModel(areaName: String): RandomMissionHistoryUiModel {
    return RandomMissionHistoryUiModel(
        commercialAreaName = areaName,
        createdAt = DateConverter.formatDate(
            input = createdAt,
            outputPattern = "yyyy.MM.dd HH:mm"
        ),
        currentUserEmojis = currentUserEmojis,
        hateCount = hateCount,
        likeCount = likeCount,
        missionHistoryId = missionHistoryId,
        submitImageId = submitImageId,
        title = title,
        user = user,
        viewCount = viewCount
    )
}