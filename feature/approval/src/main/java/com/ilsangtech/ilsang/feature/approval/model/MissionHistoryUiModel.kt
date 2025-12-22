package com.ilsangtech.ilsang.feature.approval.model

import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.util.DateConverter

data class MissionHistoryUiModel(
    val missionHistoryId: Int,
    val questId: Int,
    val commercialAreaName: String,
    val createdAt: String,
    val currentUserEmojis: List<String>,
    val hateCount: Int,
    val likeCount: Int,
    val submitImageId: String,
    val title: String,
    val writerName: String,
    val user: MissionHistoryUser,
    val viewCount: Int,
    val questType: QuestType,
    val commentCount: Int,
    val shareCount: Int,
    val lastCompleteDate: String?,
    val expireDate: String?,
    val isIsZoneQuest: Boolean
)

internal fun RandomMissionHistory.toUiModel(
    areaName: String,
    isIsZoneQuest: Boolean
): MissionHistoryUiModel {
    return MissionHistoryUiModel(
        missionHistoryId = missionHistoryId,
        questId = 0, // TODO: 실제 quest id 적용 필요
        commercialAreaName = areaName,
        createdAt = DateConverter.formatDate(
            input = createdAt,
            outputPattern = "yyyy.MM.dd HH:mm"
        ),
        currentUserEmojis = currentUserEmojis,
        hateCount = hateCount,
        likeCount = likeCount,
        submitImageId = submitImageId,
        title = title,
        writerName = writerName,
        user = user,
        viewCount = viewCount,
        questType = questType,
        commentCount = commentCount,
        shareCount = shareCount,
        lastCompleteDate = lastCompleteDate,
        expireDate = expireDate,
        isIsZoneQuest = isIsZoneQuest
    )
}