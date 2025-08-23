package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.network.model.user.UserPointResponse

internal fun UserPointResponse.toUserPoint(): UserPoint {
    return UserPoint(
        commercialAreaPoint = commercialAreaPoint,
        completedQuestCount = completedQuestCount,
        contributionPoint = contributionPoint,
        metroAreaPoint = metroAreaPoint
    )
}