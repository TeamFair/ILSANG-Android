package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class TopUsersResponse(
    val `data`: List<UserRankNetworkModel>,
    val message: String,
    val page: Int,
    val size: Int,
    val status: String,
    val total: Int
)