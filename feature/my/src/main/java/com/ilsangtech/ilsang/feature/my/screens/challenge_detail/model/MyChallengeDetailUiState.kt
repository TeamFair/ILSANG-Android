package com.ilsangtech.ilsang.feature.my.screens.challenge_detail.model

data class MyChallengeDetailUiState(
    val missionHistoryId: Int,
    val title: String,
    val submitImageId: String,
    val questImageId: String?,
    val likeCount: Int
)