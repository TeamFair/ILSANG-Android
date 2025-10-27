package com.ilsangtech.ilsang.core.network.model.mission

import com.ilsangtech.ilsang.core.network.model.quiz.CompletedQuizNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class UserMissionHistoryDetailNetworkModel(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String?,
    val questImageId: String?,
    val viewCount: Int,
    val likeCount: Int,
    val createdAt: String,
    val commercialAreaCode: String,
    val missionType: String,
    val writerName: String,
    val commercialGainPoint: Int,
    val metroGainPoint: Int,
    val contributionGainPoint: Int,
    val quizList: List<CompletedQuizNetworkModel>,
    val questType: String
)