package com.ilsangtech.ilsang.core.model

data class UserInfo(
    val completeChallengeCount: Int,
    val couponCount: Int,
    val nickname: String,
    val profileImage: String?,
    val status: String,
    val xpPoint: Int,
    val title: Title? = null
)
