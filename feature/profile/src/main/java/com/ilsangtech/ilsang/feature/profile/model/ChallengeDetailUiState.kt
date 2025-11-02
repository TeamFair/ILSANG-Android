package com.ilsangtech.ilsang.feature.profile.model

import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistoryDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quiz.CompletedQuiz
import com.ilsangtech.ilsang.core.util.DateConverter

sealed interface ChallengeDetailUiState {
    data object Loading : ChallengeDetailUiState
    data class Success(val data: ChallengeDetailUiModel) : ChallengeDetailUiState
    data object Error : ChallengeDetailUiState
}

data class ChallengeDetailUiModel(
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
    val questType: QuestType,
    val isContributionDouble: Boolean
)

internal fun UserMissionHistoryDetail.toUiModel(): ChallengeDetailUiModel {
    return ChallengeDetailUiModel(
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
        questType = questType,
        isContributionDouble = isContributionDouble
    )
}