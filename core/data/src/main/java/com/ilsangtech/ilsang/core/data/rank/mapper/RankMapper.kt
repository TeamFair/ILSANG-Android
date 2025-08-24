package com.ilsangtech.ilsang.core.data.rank.mapper

import com.ilsangtech.ilsang.core.data.title.mapper.toTitle
import com.ilsangtech.ilsang.core.model.rank.UserRank
import com.ilsangtech.ilsang.core.network.model.rank.UserRankNetworkModel

internal fun UserRankNetworkModel.toUserRank(): UserRank {
    return UserRank(
        userId = userId,
        nickName = nickName,
        point = point,
        profileImageId = profileImageId,
        rank = rank,
        title = title?.toTitle()
    )
}