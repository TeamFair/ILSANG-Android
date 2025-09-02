package com.ilsangtech.ilsang.core.data.rank.mapper

import com.ilsangtech.ilsang.core.model.rank.UserRanksWithMyRank
import com.ilsangtech.ilsang.core.network.model.rank.CommercialTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.MetroTopRankUsersResponse
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel

internal fun CommercialTopRankUsersResponse.toUserRanksWithMyRank(): UserRanksWithMyRank {
    return UserRanksWithMyRank(
        userRanks = ranks.map(UserRankNetworkModel::toUserRank),
        myRank = user.toMyAreaRank()
    )
}

internal fun MetroTopRankUsersResponse.toUserRanksWithMyRank(): UserRanksWithMyRank {
    return UserRanksWithMyRank(
        userRanks = ranks.map(UserRankNetworkModel::toUserRank),
        myRank = user.toMyAreaRank()
    )
}