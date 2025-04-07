package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class UserRankNetworkModel(
    val customerId: String,
    val lank: Int,
    val nickname: String,
    val profileImage: String?,
    val xpSum: Int
)