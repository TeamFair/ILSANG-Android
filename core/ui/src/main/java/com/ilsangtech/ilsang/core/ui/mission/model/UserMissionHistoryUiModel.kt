package com.ilsangtech.ilsang.core.ui.mission.model

import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.util.DateConverter

data class UserMissionHistoryUiModel(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String,
    val createdAt: String
)

fun UserMissionHistory.toUiModel(): UserMissionHistoryUiModel {
    return UserMissionHistoryUiModel(
        missionHistoryId = missionHistoryId,
        title = title,
        submitImageId = submitImageId,
        createdAt = DateConverter.formatDate(input = createdAt)
    )
}