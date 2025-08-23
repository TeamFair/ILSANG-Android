package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import com.ilsangtech.ilsang.core.network.model.user.UserPointResponse
import com.ilsangtech.ilsang.core.network.model.user.UserPointSummaryResponse

internal fun UserPointResponse.toUserPoint(): UserPoint {
    return UserPoint(
        commercialAreaPoint = commercialAreaPoint,
        completedQuestCount = completedQuestCount,
        contributionPoint = contributionPoint,
        metroAreaPoint = metroAreaPoint
    )
}

internal fun UserPointSummaryResponse.toUserPointSummary(): UserPointSummary {
    return UserPointSummary(
        topCommercialAreaCode = topCommercialAreaCode,
        topContributionPoint = topContributionPoint,
        topMetroAreaCode = topMetroAreaCode
    )
}