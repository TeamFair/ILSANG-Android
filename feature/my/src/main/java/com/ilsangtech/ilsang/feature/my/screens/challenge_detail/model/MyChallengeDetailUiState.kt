package com.ilsangtech.ilsang.feature.my.screens.challenge_detail.model

import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistoryDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quiz.CompletedQuiz
import com.ilsangtech.ilsang.core.util.DateConverter

sealed interface MyChallengeDetailUiState {
    data class Success(val data: MyChallengeDetailUiModel) : MyChallengeDetailUiState
    data object Error : MyChallengeDetailUiState
    object Loading : MyChallengeDetailUiState
}

data class MyChallengeDetailUiModel(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String?,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String,
    val commercialAreaCode: String,
    val missionType: MissionType,
    val writerName: String,
    val commercialGainPoint: Int,
    val metroGainPoint: Int,
    val contributionGainPoint: Int,
    val quiz: CompletedQuiz?,
    val questType: QuestType
)

internal fun UserMissionHistoryDetail.toUiModel(): MyChallengeDetailUiModel {
    return MyChallengeDetailUiModel(
        missionHistoryId = missionHistoryId,
        title = title,
        submitImageId = submitImageId,
        viewCount = viewCount,
        likeCount = likeCount,
        createdAt = DateConverter.formatDate(createdAt),
        commercialAreaCode = commercialAreaCode,
        missionType = missionType,
        writerName = writerName,
        commercialGainPoint = commercialGainPoint,
        metroGainPoint = metroGainPoint,
        contributionGainPoint = contributionGainPoint,
        quiz = quiz,
        questType = questType
    )
}