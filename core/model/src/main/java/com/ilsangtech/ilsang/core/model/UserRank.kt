package com.ilsangtech.ilsang.core.model

data class UserRank(
    val customerId: String,
    val nickname: String,
    val xpSum: Int,
    val profileImageUrl: String? = null,
    val rank: Int
)
