package com.ilsangtech.ilsang.core.model.mission

import com.ilsangtech.ilsang.core.model.quest.QuestType
import com.ilsangtech.ilsang.core.model.quiz.CompletedQuiz

data class UserMissionHistoryDetail(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String?,
    val questImageId: String?,
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
