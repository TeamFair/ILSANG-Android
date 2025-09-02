package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class CommercialTopRankUsersResponse(
    val ranks: List<UserRankNetworkModel>,
    val user: MyAreaRankNetworkModel
)