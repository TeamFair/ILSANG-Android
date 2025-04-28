package com.ilsangtech.ilsang.core.network.model.challenge

import kotlinx.serialization.Serializable

@Serializable
data class RandomChallengeNetworkModel(
    val challengeId: String,
    val createdAt: String,
    val customerId: String,
    val hateCnt: Int,
    val likeCnt: Int,
    val missionTitle: String,
    val receiptImageId: String,
    val status: String,
    val userNickName: String,
    val userProfileImage: String,
    val viewCount: Int
)