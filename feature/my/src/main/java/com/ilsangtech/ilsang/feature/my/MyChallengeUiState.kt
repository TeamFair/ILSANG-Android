package com.ilsangtech.ilsang.feature.my

data class MyChallengeUiState(
    val challengeId: String,
    val title: String,
    val receiptImageId: String?,
    val questImageId: String?,
    val viewCount: Int,
    val likeCount: Int
)
