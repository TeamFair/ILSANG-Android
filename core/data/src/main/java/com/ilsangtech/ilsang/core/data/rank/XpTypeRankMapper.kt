package com.ilsangtech.ilsang.core.data.rank

import com.ilsangtech.ilsang.core.model.UserXpTypeRank
import com.ilsangtech.ilsang.core.network.model.rank.XpTypeRankNetworkModel

fun XpTypeRankNetworkModel.toUserXpTypeRank(): UserXpTypeRank {
    return UserXpTypeRank(
        customerId = customerId,
        nickname = nickname,
        profileImage = profileImage,
        xpPoint = xpPoint,
        xpType = xpType
    )
}