package com.ilsangtech.ilsang.core.model

data class MyInfo(
    val nickname: String? = null,
    val email: String? = null,
    val profileImage: String? = null,
    val completeChallengeCount: Int,
    val couponCount: Int,
    val xpPoint: Int,
    val status: String,
    val title: Title?
)
