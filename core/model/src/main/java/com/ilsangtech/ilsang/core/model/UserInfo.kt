package com.ilsangtech.ilsang.core.model

data class UserInfo(
    val accessToken: String,
    val authorization: String? = null,
    val nickname: String? = null,
    val email: String? = null,
    val profileImage: String? = null,
    val completeChallengeCount: Int,
    val couponCount: Int,
    val status: String
)
