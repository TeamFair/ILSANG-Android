package com.ilsangtech.ilsang.feature.approval.model

import com.ilsangtech.ilsang.core.model.mission.MissionHistoryUser
import com.ilsangtech.ilsang.core.model.mission.RandomMissionHistory
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.util.DateConverter
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
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
    val isIsZoneQuest: Boolean,
    val missionExecutionUiState: MissionExecutionUiState = MissionExecutionUiState.Available
)

internal fun RandomMissionHistory.toUiModel(
    areaName: String,
    isIsZoneQuest: Boolean
): MissionHistoryUiModel {
    val isExpired = expireDate?.let { dateStr ->
        DateConverter.parseDate(dateStr).before(Date())
    } ?: false
    val isCompleted = lastCompleteDate?.let { dateStr ->
        when (questType) {
            is QuestType.Repeat.Daily -> {
                DateConverter.getRemainHours(
                    date = dateStr,
                    day = 1
                ) > 0
            }

            is QuestType.Repeat.Weekly -> {
                DateConverter.getRemainHours(
                    date = dateStr,
                    week = 1
                ) > 0
            }

            is QuestType.Repeat.Monthly -> {
                DateConverter.getRemainHours(
                    date = dateStr,
                    month = 1
                ) > 0
            }

            else -> true
        }
    } ?: false

    return MissionHistoryUiModel(
        missionHistoryId = missionHistoryId,
        questId = questId,
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
        isIsZoneQuest = isIsZoneQuest,
        missionExecutionUiState = when {
            isExpired -> MissionExecutionUiState.Expired
            isCompleted -> MissionExecutionUiState.Completed
            else -> MissionExecutionUiState.Available
        }
    )
}