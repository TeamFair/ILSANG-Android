package com.ilsangtech.ilsang.core.model

data class RandomChallenge(
    val challengeId: String,
    val createdAt: String,
    val customerId: String,
    val hateCnt: Int,
    val likeCnt: Int,
    val missionTitle: String,
    val receiptImageId: String,
    val status: String,
    val userNickName: String,
    val userProfileImage: String?,
    val viewCount: Int,
    val emoji: Emoji?
)