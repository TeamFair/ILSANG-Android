package com.ilsangtech.ilsang.core.ui.mission.model

import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistory
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.util.DateConverter

data class UserMissionHistoryUiModel(
    val missionHistoryId: Int,
    val title: String,
    val questImageId: String?,
    val submitImageId: String,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String,
    //TODO QuestType, MissionType을 도메인 모델에서 받을 수 있도록 적용 필요
    val questType: QuestType = QuestType.Repeat.Daily,
    val missionType: MissionType = MissionType.Photo
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