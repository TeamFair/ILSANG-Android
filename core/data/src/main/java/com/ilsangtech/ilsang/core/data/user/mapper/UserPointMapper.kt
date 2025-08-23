package com.ilsangtech.ilsang.core.data.user.mapper

import com.ilsangtech.ilsang.core.model.user.TopCommercialArea
import com.ilsangtech.ilsang.core.model.user.UserCommercialPoint
import com.ilsangtech.ilsang.core.model.user.UserPoint
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import com.ilsangtech.ilsang.core.network.model.user.UserCommercialPointResponse
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

internal fun UserCommercialPointResponse.toUserCommercialPoint(): UserCommercialPoint {
    return UserCommercialPoint(
        topCommercialArea = TopCommercialArea(
            commercialAreaCode = topCommercialArea.commercialAreaCode,
            ownerContributionPercent = topCommercialArea.ownerContributionPercent,
            point = topCommercialArea.point
        ),
        totalOwnerContributions = totalOwnerContributions.map { contribution ->
            com.ilsangtech.ilsang.core.model.user.TotalOwnerContribution(
                commercialAreaCode = contribution.commercialAreaCode,
                point = contribution.point
            )
        }
    )
}