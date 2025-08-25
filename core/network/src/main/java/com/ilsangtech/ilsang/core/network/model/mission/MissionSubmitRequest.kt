package com.ilsangtech.ilsang.core.network.model.mission

import kotlinx.serialization.Serializable

@Serializable
data class MissionSubmitRequest(
    val missionId: Int,
    val imageId: String?,
    val quizId: Int?,
    val answer: String?
)
