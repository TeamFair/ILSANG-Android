package com.ilsangtech.ilsang.core.data.rank

import com.ilsangtech.ilsang.core.model.UserRank
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel

fun UserRankNetworkModel.toUserRank(): UserRank {
    return UserRank(
        customerId = customerId,
        nickname = nickname,
        rank = lank,
        profileImageUrl = profileImage,
        xpSum = xpSum
    )
}