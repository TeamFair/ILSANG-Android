package com.ilsangtech.ilsang.core.data.mission.mapper

import com.ilsangtech.ilsang.core.data.quest.mapper.fromString
import com.ilsangtech.ilsang.core.data.quiz.mapper.toCompletedQuiz
import com.ilsangtech.ilsang.core.model.mission.MissionType
import com.ilsangtech.ilsang.core.model.mission.UserMissionHistoryDetail
import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.network.model.mission.UserMissionHistoryDetailNetworkModel

internal fun UserMissionHistoryDetailNetworkModel.toUserMissionHistoryDetail(): UserMissionHistoryDetail {
    return UserMissionHistoryDetail(
        missionHistoryId = missionHistoryId,
        title = title,
        submitImageId = submitImageId,
        questImageId = questImageId,
        viewCount = viewCount,
        likeCount = likeCount,
        createdAt = createdAt,
        commercialAreaCode = commercialAreaCode,
        missionType = MissionType.fromString(missionType),
        writerName = writerName,
        commercialGainPoint = commercialGainPoint,
        metroGainPoint = metroGainPoint,
        contributionGainPoint = contributionGainPoint,
        quiz = quizList.firstOrNull()?.toCompletedQuiz(),
        questType = QuestType.fromString(
            type = questType,
            repeatFrequency = repeatFrequency
        ),
        isContributionDouble = contributionDoublePointYn
    )
}