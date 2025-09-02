package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class MetroTopRankUsersResponse(
    val ranks: List<UserRankNetworkModel>,
    val user: MyAreaRankNetworkModel
)