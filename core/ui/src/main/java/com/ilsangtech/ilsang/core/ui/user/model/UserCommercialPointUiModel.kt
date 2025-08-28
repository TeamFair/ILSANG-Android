package com.ilsangtech.ilsang.core.ui.user.model

import com.ilsangtech.ilsang.core.model.user.TopCommercialArea
import com.ilsangtech.ilsang.core.model.user.TotalOwnerContribution

data class UserCommercialPointUiModel(
    val nickname: String,
    val topCommercialArea: TopCommercialAreaUiModel?,
    val totalOwnerContributions: List<TotalOwnerContributionUiModel>
)

data class TopCommercialAreaUiModel(
    val commercialAreaName: String,
    val contributionPercent: Int,
    val point: Int
)

data class TotalOwnerContributionUiModel(
    val commercialAreaName: String,
    val point: Int
)

fun TopCommercialArea.toUiModel(areaName: String): TopCommercialAreaUiModel {
    return TopCommercialAreaUiModel(
        commercialAreaName = areaName,
        contributionPercent = ownerContributionPercent,
        point = point
    )
}

fun TotalOwnerContribution.toUiModel(areaName: String): TotalOwnerContributionUiModel {
    return TotalOwnerContributionUiModel(
        commercialAreaName = areaName,
        point = point
    )
}