package com.ilsangtech.ilsang.feature.my.screens.mytab.model

import com.ilsangtech.ilsang.core.model.season.Season
import com.ilsangtech.ilsang.core.model.user.UserPointSummary
import com.ilsangtech.ilsang.core.util.DateConverter

data class MyPointSummaryUiModel(
    val nickName: String,
    val topCommercialAreaName: String?,
    val topMetroAreaName: String?,
    val topContributionPoint: Int?,
    val seasonNumber: Int,
    val seasonStartDate: String,
    val seasonEndDate: String
)

internal suspend fun UserPointSummary.toMyPointSummaryUiModel(
    nickName: String,
    season: Season,
    changeCodeToMetroName: suspend (String) -> String,
    changeCodeToCommercialName: suspend (String) -> String
): MyPointSummaryUiModel {
    return MyPointSummaryUiModel(
        nickName = nickName,
        topCommercialAreaName = topCommercialAreaCode?.let { changeCodeToCommercialName(it) },
        topMetroAreaName = topMetroAreaCode?.let { changeCodeToMetroName(it) },
        topContributionPoint = topContributionPoint,
        seasonNumber = season.seasonNumber,
        seasonStartDate = DateConverter.formatDate(season.startDate),
        seasonEndDate = DateConverter.formatDate(season.endDate)
    )
}