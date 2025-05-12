package com.ilsangtech.ilsang.core.model

data class UserXpTypeRank(
    val customerId: String,
    val nickname: String,
    val profileImage: String?,
    val xpPoint: Int,
    val xpType: String
)