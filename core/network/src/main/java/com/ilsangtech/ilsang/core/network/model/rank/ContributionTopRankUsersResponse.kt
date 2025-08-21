package com.ilsangtech.ilsang.core.network.model.rank

import kotlinx.serialization.Serializable

@Serializable
data class ContributionTopRankUsersResponse(
    val ranks: List<UserRankNetworkModel>,
    val user: UserRankNetworkModel
)
