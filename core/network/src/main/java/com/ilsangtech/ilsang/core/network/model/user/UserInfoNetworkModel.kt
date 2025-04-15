package com.ilsangtech.ilsang.core.network.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoNetworkModel(
    val completeChallengeCount: Int,
    val couponCount: Int,
    val nickname: String,
    val profileImage: String?,
    val status: String,
    val xpPoint: Int
)