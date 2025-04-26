package com.ilsangtech.ilsang.core.model

data class Challenge(
    val challengeId: String,
    val createdAt: String,
    val hateCnt: Int,
    val likeCnt: Int,
    val missionTitle: String,
    val questImage: String?,
    val receiptImageId: String?,
    val status: String,
    val userNickName: String,
    val viewCount: Int
)
