package com.ilsangtech.ilsang.core.ui.mission.model

import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.util.DateConverter

data class UserMissionHistoryUiModel(
    val missionHistoryId: Int,
    val title: String,
    val questImageId: String?,
    val submitImageId: String,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String
)

fun UserMissionHistory.toUiModel(): UserMissionHistoryUiModel {
    return UserMissionHistoryUiModel(
        missionHistoryId = missionHistoryId,
        title = title,
        questImageId = questImageId,
        submitImageId = submitImageId,
        viewCount = viewCount,
        likeCount = likeCount,
        createdAt = DateConverter.formatDate(input = createdAt)
    )
}